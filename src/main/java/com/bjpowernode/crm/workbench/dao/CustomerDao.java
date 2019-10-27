package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer showCustomerById(String company);

    int insert(Customer cus);

    List<String> getCustomerNameListByName(String name);
}
