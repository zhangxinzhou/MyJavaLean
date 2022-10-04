package com.example.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    public static final String USER_INFO = "user";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object userInfo = request.getSession().getAttribute(USER_INFO);
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
