package com.bjpowernode.crm.workbench.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 王硕
 * 2019/10/26
 */
public class TransactionController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        String path =requset.getServletPath();
        if("/workbench/TransactionServise/getCustomerName.do".equals(path)){
            getCustomerName(requset,response);
        }
    }

    private void getCustomerName(HttpServletRequest requset, HttpServletResponse response) {

    }
}
