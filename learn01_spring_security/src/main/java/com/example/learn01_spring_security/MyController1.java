package com.example.learn01_spring_security;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController1 {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @Secured({"ROLE_管理员", "ROLE_访客"})
    @RequestMapping("/toMain")
    @ResponseBody
    public String toMain() {
        return "main";
    }

    @RequestMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        return "loginSuccess";
    }

    @RequestMapping("/loginFail")
    @ResponseBody
    public String loginFail() {
        return "loginFail";
    }
}
