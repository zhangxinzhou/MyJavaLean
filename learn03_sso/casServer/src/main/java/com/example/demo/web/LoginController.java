package com.example.demo.web;

import com.example.demo.entity.UserForm;
import com.example.demo.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {
    public static final String SSO_COOKIE_NAME = "SSO_TOKEN";
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/toLogin")
    public String toLogin(Model model, HttpServletRequest request, HttpServletResponse response) {
        UserForm userInfo = (UserForm) request.getSession().getAttribute(LoginFilter.USER_INFO);
        // 不为空,则是已登录状态
        String redirectUrl = request.getParameter("url");
        if (null != userInfo &&
                userInfo.getBackurl() != null &&
                userInfo.getBackurl().trim().length() != 0 &&
                redirectUrl != null) {
            String ssoToken = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(ssoToken, userInfo, 120, TimeUnit.SECONDS);

            Cookie cookie = new Cookie(SSO_COOKIE_NAME, ssoToken);
            response.addCookie(cookie);
            return "redirect:" + redirectUrl;
        }

        UserForm user = new UserForm();
        user.setBackurl(request.getParameter("url"));
        model.addAttribute("user", user);
        return "login";
    }


    @PostMapping("/login")
    public void login(@ModelAttribute UserForm user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("backurl:" + user.getBackurl());
        request.getSession().setAttribute(LoginFilter.USER_INFO, user);

        // 登录成功,创建用户信息票据
        String ssoToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(ssoToken, user, 120, TimeUnit.SECONDS);

        // 重定向,回原url ---a.com
        if (null == user.getBackurl() || user.getBackurl().length() == 0) {
            response.sendRedirect("/index");
        } else {
            String backUrl = user.getBackurl();
            Cookie cookie = new Cookie(SSO_COOKIE_NAME, ssoToken);
            response.addCookie(cookie);
            response.sendRedirect(backUrl);
        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserForm userInfo = (UserForm) request.getSession().getAttribute(LoginFilter.USER_INFO);
        // 登出,删除ssoToken
        String ssoToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SSO_COOKIE_NAME.equals(cookie.getName())) {
                    ssoToken = cookie.getValue();
                    redisTemplate.delete(ssoToken);
                }
            }
        }

        // 清空cookie
        Cookie cookie = new Cookie(SSO_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return userInfo.getUsername() + ",bye!";
    }
}
