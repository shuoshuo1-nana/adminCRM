package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 王硕
 * 2019/10/20
 */
public interface UserDao {

    User getUser(Map<String, String> map);

    List<User> getUsers();
}
