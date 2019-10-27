package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ContactsDao;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.service.TransactionServise;

import java.util.List;

/**
 * Author: 王硕
 * 2019/10/26
 */
public class TransactionServiseImpl implements TransactionServise {
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<Contacts> getContactsListByName(String fullname) {

        List<Contacts> cList = contactsDao.getContactsListByName(fullname);

        for(Contacts contacts:cList){

            //取得所有的邮箱和手机号，如果是null值，需要处理成为-

            String email = contacts.getEmail();
            if(email==null){

                contacts.setEmail("-");

            }

            String mphone = contacts.getMphone();
            if(mphone==null){

                contacts.setMphone("-");

            }

        }

        return cList;
    }

    @Override
    public List<String> getCustomerNameListByName(String name) {

        List<String> sList = customerDao.getCustomerNameListByName(name);

        return sList;
    }
}
