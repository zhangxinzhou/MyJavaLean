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
    @RequestMapping("/index")
    @ResponseBody
    public Object index(HttpServletRequest request) {
        Object user = request.getSession().getAttribute(LoginFilter.USER_INFO);
        return user;
    }

    @RequestMapping("/")
    @ResponseBody
    public Object welcome(HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(LoginFilter.USER_INFO);
        return userInfo;
    }

}
