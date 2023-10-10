package com.example.demo.service;

import com.example.demo.dao.master.PgDao;
import com.example.demo.dao.second.MysqlDao;
import com.example.demo.multitransaction.MultiDataSourceTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiDataSourceTransactionService {

    /**
     * 是否抛出异常,抛异常事务才会回滚
     */
    public static final boolean flag = true;

    @Autowired
    PgDao pgDao;

    @Autowired
    MysqlDao mysqlDao;

    // TODO mysql插入两次,只有第一次会插入,这个问题很致命,有时间要解决下

    @MultiDataSourceTransactional(transactionManagers = {"masterTransactionManager", "secondTransactionManager"})
    public void multiDataSourceTransactionTest1() {

        pgDao.insertOne();
        mysqlDao.insertOne();

        if (flag) {
            throw new RuntimeException("事务测试");
        }

        pgDao.insertOne();
        mysqlDao.insertOne();

    }

    @MultiDataSourceTransactional(transactionManagers = {"masterTransactionManager", "secondTransactionManager"})
    public void multiDataSourceTransactionTest2() {

        mysqlDao.insertOne();
        pgDao.insertOne();


        if (flag) {
            throw new RuntimeException("事务测试");
        }

        mysqlDao.insertOne();
        pgDao.insertOne();

    }

    @MultiDataSourceTransactional(transactionManagers = {"masterTransactionManager", "secondTransactionManager"})
    public void pgDoubleTest() {
        pgDao.insertOne();
        pgDao.insertOne();
    }

    @MultiDataSourceTransactional(transactionManagers = {"masterTransactionManager", "secondTransactionManager"})
    public void mysqlDoubleTest() {
        mysqlDao.insertOne();
        mysqlDao.insertOne();
    }


}
