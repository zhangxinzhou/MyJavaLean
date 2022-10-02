package com.example.learn01_spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;


    public User login(String userName) {
        String password = passwordEncoder.encode(userName);
        return new User(userName,password);
    }
}
