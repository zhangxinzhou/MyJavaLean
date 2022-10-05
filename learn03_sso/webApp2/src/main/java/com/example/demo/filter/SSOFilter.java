package com.example.demo.filter;

import com.example.demo.entity.UserForm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "SSOFilter")
public class SSOFilter implements Filter {

    public static final String USER_INFO = "user";

    private RedisTemplate redisTemplate;

    public SSOFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object userInfo = request.getSession().getAttribute(USER_INFO);

        // 如果未登录,则拒绝请求,转向登录页面
        String requestUrl = request.getServletPath();
        if (!"/toLogin".equals(requestUrl) // 不是登录页面
                && !requestUrl.startsWith("/login") // 不是去登录
                && null == userInfo) { // 不是登录状态

            String ticket = request.getParameter("ticket");
            // 有票据,则使用票据去尝试拿取用户信息
            if (null != ticket) {
                userInfo = redisTemplate.opsForValue().get(ticket);
            }
            // 无法得到用户信息,则去登陆页面
            if (null == userInfo) {
                response.sendRedirect("http://localhost:8880/toLogin?url=" + request.getRequestURL().toString());
                return;
            }

            /**
             * 将用户信息,加载进session中
             */
            UserForm user = (UserForm) userInfo;
            request.getSession().setAttribute(SSOFilter.USER_INFO, user);
            redisTemplate.delete(ticket);
        }

        filterChain.doFilter(request, servletResponse);
    }
}
