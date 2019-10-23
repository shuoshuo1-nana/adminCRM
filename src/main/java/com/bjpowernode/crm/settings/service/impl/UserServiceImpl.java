package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.Exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 王硕
 * 2019/10/20
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User getLogin(String loginact, String loginpwd, String ip) throws LoginException {
        Map<String, String> map = new HashMap<>();
        map.put("loginact", loginact);
        map.put("loginpwd", loginpwd);
        User user = dao.getUser(map);
        //判断账户是否正确
        if(user==null){
            //账号密码错误
            //为控制器抛出异常，以及相应的异常信息
            throw new LoginException("账号密码错误");

        }
        //判断失效时间是否为当前时间
        if (user.getExpireTime().compareTo(DateTimeUtil.getSysTime()) < 0) {
            throw new LoginException("账户已失效，请联系管理员");
        }
        //判断账户是否被锁定
        if ("0".equals(user.getLockState())) {
            //失效
            throw new LoginException("账户已经被锁定，请联系管理员");

        }
        //未被锁定
        if (!user.getAllowIps().contains(ip)) {
            throw new LoginException("ip地址不是常用地址，请联系管理员");
        }
        return user;


    }

    @Override
    public List<User> getUsers() {
        List<User> slist =dao.getUsers();
        return slist;
    }


}
