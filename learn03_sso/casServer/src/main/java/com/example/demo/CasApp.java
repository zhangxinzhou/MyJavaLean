package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 参考:https://mp.weixin.qq.com/s/TBVtYPrbLMJyaUulLSVx9g
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CasApp {

	public static void main(String[] args) {
		SpringApplication.run(CasApp.class, args);
	}

}
