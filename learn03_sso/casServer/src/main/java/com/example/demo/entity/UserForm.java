package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserForm {
    private String username;
    private String password;
    private String backurl;
}
