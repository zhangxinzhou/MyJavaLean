package com.example.demo.dao.second;

import java.util.List;

public interface MysqlDao {

    List<Object> selectList();

    Integer getSeq();
}
