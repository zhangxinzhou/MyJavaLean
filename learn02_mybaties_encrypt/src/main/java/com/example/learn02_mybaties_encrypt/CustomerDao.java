package com.example.learn02_mybaties_encrypt;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDao {
    void insertCustomer(Customer customer);

    List<Customer> findCustomer(@Param("phone") Encrypt phone);

    List<Customer> findAll();
}
