package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.Exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 王硕
 * 2019/10/20
 */
public interface UserService {
    User getLogin(String loginact, String loginpwd,String ip) throws LoginException;


    List<User> getUsers();


    List<User> getUserList();
}
