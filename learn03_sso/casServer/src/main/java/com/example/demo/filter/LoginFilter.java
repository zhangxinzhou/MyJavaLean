package com.example.demo.filter;

import com.example.demo.entity.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "SSOFilter")
public class LoginFilter implements Filter {

    public static final String USER_INFO = "user";

    public static final String SSO_COOKIE_NAME = "SSO_TOKEN";

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("casServer.LoginFilter...开始处理");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 从cookie中获取token
        String ssoToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SSO_COOKIE_NAME.equals(cookie.getName())) {
                    ssoToken = cookie.getValue();
                    break;
                }
            }
        }

        // 检查token是否有效
        Object userInfo = null;
        if (ssoToken != null) {
            userInfo = redisTemplate.opsForValue().get(ssoToken);
            if (userInfo == null) {
                // 删除无效的ssoToken和cookie
                redisTemplate.delete(ssoToken);
                Cookie cookie = new Cookie(SSO_COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        /**
         * 将用户信息,加载进session中
         */
        if (userInfo != null) {
            UserForm user = (UserForm) userInfo;
            request.getSession().setAttribute(LoginFilter.USER_INFO, user);
        }

        // 如果未登录,则拒绝请求,转向登录页面
        String requestUrl = request.getServletPath();
        if (!"/toLogin".equals(requestUrl) // 不是登录页面
                && !requestUrl.startsWith("/login") // 不是去登录
                && null == userInfo) {// 不是登录状态
            request.getRequestDispatcher("/toLogin").forward(request, response);
            return;
        }

        filterChain.doFilter(request, servletResponse);
    }
}
