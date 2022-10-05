package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
public class MyTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        List clientList = redisTemplate.getClientList();
        clientList.forEach(System.out::println);
    }
}
