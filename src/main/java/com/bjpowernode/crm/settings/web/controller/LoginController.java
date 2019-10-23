package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.Exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 王硕
 * 2019/10/20
 */
public class LoginController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("用户模块控制器");
        String path =request.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            login(request, response);
        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
       String loginact =  request.getParameter("loginact");
       String loginpwd =  request.getParameter("loginpwd");
       String ip = request.getRemoteAddr();
        loginpwd = MD5Util.getMD5(loginpwd);
        UserService sss = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user =sss.getLogin(loginact,loginpwd,ip);
            PrintJson.printJsonFlag(response, true);
            request.getSession().setAttribute("user", user);
        } catch (LoginException e) {
            System.out.println("登录异常");
            String mgs = e.getMessage();
            PrintJson.printJsonObj(response, mgs);
        }

    }

}
