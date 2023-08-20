package com.example.demo.web;

import com.example.demo.filter.SSOFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
