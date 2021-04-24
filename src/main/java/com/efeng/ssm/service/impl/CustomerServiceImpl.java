package com.efeng.ssm.service.impl;

import com.efeng.ssm.mapper.CustomerMapper;
import com.efeng.ssm.mapper.CustomerRemarkMapper;
import com.efeng.ssm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerRemarkMapper customerRemarkMapper;

    @Override
    public List<String> getCustomerName(String name) {
        return customerMapper.findCustomerName(name);
    }
}
