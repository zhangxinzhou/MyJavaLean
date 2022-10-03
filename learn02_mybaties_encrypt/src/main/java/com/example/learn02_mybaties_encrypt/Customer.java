package com.example.learn02_mybaties_encrypt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;
    private Encrypt phone;
    private String address;
    private String createdDate;
}
