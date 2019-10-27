package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

/**
 * Author: 王硕
 * 2019/10/26
 */
public interface TransactionServise {
    List<Contacts> getContactsListByName(String fullname);

    List<String> getCustomerNameListByName(String name);
}
