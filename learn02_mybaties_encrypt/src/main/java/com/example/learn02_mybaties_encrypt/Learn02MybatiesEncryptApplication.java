package com.example.learn02_mybaties_encrypt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@MapperScan("com.example.*")
@SpringBootApplication
public class Learn02MybatiesEncryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(Learn02MybatiesEncryptApplication.class, args);
    }

}
