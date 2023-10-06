package com.example.demo.service;

import com.example.demo.dao.master.PgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PgService {

    @Autowired
    PgDao pgDao;

    public List<Object> getList(){
        return pgDao.selectList();
    }
}
