package com.example.demo.web;

import com.example.demo.entity.UserForm;
import com.example.demo.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/toLogin")
    public String toLogin(Model model, HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(LoginFilter.USER_INFO);
        // 不为空,则是已登录状态
        if (null != userInfo) {
            String ticket = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(ticket, userInfo, 2, TimeUnit.SECONDS);
            return "redirect:" + request.getParameter("url") + "?ticket=" + ticket;
        }
        UserForm user = new UserForm();
        user.setUsername("laowang");
        user.setPassword("laowang");
        user.setBackurl(request.getParameter("url"));
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute UserForm user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("backurl:" + user.getBackurl());
        request.getSession().setAttribute(LoginFilter.USER_INFO, user);

        // 登录成功,创建用户信息票据
        String ticket = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(ticket, user, 20, TimeUnit.SECONDS);

        // 重定向,回原url ---a.com
        if (null == user.getBackurl() || user.getBackurl().length() == 0) {
            response.sendRedirect("/index");
        } else {
            response.sendRedirect(user.getBackurl() + "?ticket=" + ticket);
        }
    }

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Object user = request.getSession().getAttribute(LoginFilter.USER_INFO);
        UserForm userInfo = (UserForm) user;
        modelAndView.setViewName("index");
        modelAndView.addObject("user", userInfo);
        request.getSession().setAttribute("test", "123");
        return modelAndView;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }
}
