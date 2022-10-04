package com.example.demo.web;

import com.example.demo.entity.UserForm;
import com.example.demo.filter.SSOFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Object userInfo = request.getSession().getAttribute(SSOFilter.USER_INFO);

        UserForm user = (UserForm) userInfo;
        modelAndView.setViewName("index");
        modelAndView.addObject("user", user);

        request.getSession().setAttribute("test", "123");
        return modelAndView;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }
}
