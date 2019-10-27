package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int insert(Contacts con);

    List<Contacts> getContactsListByName(String fullname);
}
