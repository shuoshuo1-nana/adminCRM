package com.bjpowernode.crm.settings.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 王硕
 * 2019/10/20
 */
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("用户模块控制器");

        String path =request.getServletPath();
        if ("/settings/user/xxx.do".equals(path)){

        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }
}
