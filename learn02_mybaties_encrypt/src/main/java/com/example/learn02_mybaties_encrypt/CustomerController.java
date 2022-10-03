package com.example.learn02_mybaties_encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerDao customerDao;

    @RequestMapping("/add")
    @ResponseBody
    String addCustomer(Customer customer) {
        customerDao.insertCustomer(customer);
        return "add success";
    }

    @RequestMapping("/find")
    @ResponseBody
    List<Customer> find(Customer customer) {
        return customerDao.findCustomer(customer.getPhone());
    }

    @RequestMapping("/all")
    @ResponseBody
    List<Customer> all() {
        return customerDao.findAll();
    }

}
