package com.example.demo.web;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.MysqlService;
import com.example.demo.service.PgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZxzController {

    @Autowired
    PgService pgService;

    @Autowired
    MysqlService mysqlService;

    @RequestMapping("/test")
    public String test() {
        String str = "";
        List<Object> list = pgService.getList();
        List<Object> list1 = mysqlService.getList();
        str += JSON.toJSONString(list);
        str += JSON.toJSONString(list1);
        str += "\nseq=" + mysqlService.getSeq();
        return str;
    }

}
