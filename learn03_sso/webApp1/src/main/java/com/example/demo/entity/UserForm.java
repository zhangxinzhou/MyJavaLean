package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserForm implements Serializable {
    private String username;
    private String password;
    private String backurl;
}
