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

    @RequestMapping("/index")
    @ResponseBody
    public Object index(HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(SSOFilter.USER_INFO);
        return userInfo;
    }

    @RequestMapping("/")
    @ResponseBody
    public Object welcome(HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute(SSOFilter.USER_INFO);
        return userInfo;
    }
}
