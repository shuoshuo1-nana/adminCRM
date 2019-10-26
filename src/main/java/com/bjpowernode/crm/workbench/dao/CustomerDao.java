package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer showCustomerById(String company);

    int insert(Customer cus);
}
