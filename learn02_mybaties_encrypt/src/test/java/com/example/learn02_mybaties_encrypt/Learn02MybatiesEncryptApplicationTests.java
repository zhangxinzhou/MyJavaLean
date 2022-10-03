package com.example.learn02_mybaties_encrypt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@SpringBootTest
class Learn02MybatiesEncryptApplicationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    void jdbcTemplateTest() {
        String sql = "select now()";
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql);
        System.out.println(stringObjectMap);
    }
}
