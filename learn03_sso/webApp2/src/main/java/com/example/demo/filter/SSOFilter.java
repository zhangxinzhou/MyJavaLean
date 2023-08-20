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
public class SSOFilter implements Filter {
    public static final String USER_INFO = "user";
    public static final String SSO_COOKIE_NAME = "SSO_TOKEN";
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("webApp2.SSOFilter...开始处理");
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
        }

        /**
         * 将用户信息,加载进session中
         */
        if (userInfo != null) {
            UserForm user = (UserForm) userInfo;
            request.getSession().setAttribute(SSOFilter.USER_INFO, user);
        }

        // 如果未登录,则拒绝请求,转向登录页面
        String requestUrl = request.getServletPath();
        if (!"/toLogin".equals(requestUrl) // 不是登录页面
                && !requestUrl.startsWith("/login") // 不是去登录
                && null == userInfo) { // 不是登录状态

            // 无法得到用户信息,则去登陆页面
            if (null == userInfo) {
                response.sendRedirect("http://cas:8880/toLogin?url=" + request.getRequestURL().toString());
                return;
            }

            /**
             * 将用户信息,加载进session中
             */
            UserForm user = (UserForm) userInfo;
            request.getSession().setAttribute(SSOFilter.USER_INFO, user);
        }

        filterChain.doFilter(request, servletResponse);
    }
}
