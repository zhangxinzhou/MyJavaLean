package com.example.learn01_spring_security;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;

}
