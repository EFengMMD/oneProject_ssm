package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.Customer;

import java.util.List;

public interface CustomerMapper {

    Customer getCustomerByName(String company);

    boolean insertCustomer(Customer customer);

    List<String> findCustomerName(String name);

}
