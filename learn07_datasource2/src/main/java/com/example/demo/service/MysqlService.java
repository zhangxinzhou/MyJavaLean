package com.example.demo.service;

import com.example.demo.dao.second.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlService {

    @Autowired
    MysqlDao mysqlDao;

    public List<Object> getList(){
        return mysqlDao.selectList();
    }

    public Integer getSeq(){
        return mysqlDao.getSeq();
    }
}
