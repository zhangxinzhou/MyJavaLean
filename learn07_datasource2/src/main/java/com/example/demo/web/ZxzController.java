package com.example.demo.web;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.MultiDataSourceTransactionService;
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

    @Autowired
    MultiDataSourceTransactionService multiDataSourceTransactionService;

    @RequestMapping("/test")
    public String test() {
        String str = "";
        List<Object> pgList = pgService.getList();
        List<Object> mysqlList = mysqlService.getList();
        str += "pg:<br/>";
        for (Object o : pgList) {
            str += JSON.toJSONString(o) + "<br/>";
        }
        str += "mysql:<br/>";
        for (Object o : mysqlList) {
            str += JSON.toJSONString(o) + "<br/>";
        }
        str += "seq=" + mysqlService.getSeq();
        return str;
    }

    @RequestMapping("/transactionTest1")
    public String transactionTest1() {
        multiDataSourceTransactionService.multiDataSourceTransactionTest1();
        return "transactionTest1";
    }

    @RequestMapping("/transactionTest2")
    public String transactionTest2() {
        multiDataSourceTransactionService.multiDataSourceTransactionTest2();
        return "transactionTest2";
    }

    @RequestMapping("/pgDoubleTest")
    public String pgDoubleTest() {
        multiDataSourceTransactionService.pgDoubleTest();
        return "pgDoubleTest";
    }

    @RequestMapping("/mysqlDoubleTest")
    public String mysqlDoubleTest() {
        multiDataSourceTransactionService.mysqlDoubleTest();
        return "mysqlDoubleTest";
    }
}
