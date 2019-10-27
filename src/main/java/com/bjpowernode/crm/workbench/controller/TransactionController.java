package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.service.TransactionServise;
import com.bjpowernode.crm.workbench.service.impl.TransactionServiseImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/26
 */
public class TransactionController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if("/workbench/transaction/add.do".equals(path)){

            add(request,response);

        }else if("/workbench/transaction/getCustomerNameListByName.do".equals(path)){

            getCustomerNameListByName(request,response);

        }else if("/workbench/transaction/getContactsListByName.do".equals(path)){

            getContactsListByName(request,response);

        }else if("/workbench/transaction/getPossibilityByStage.do".equals(path)){

            getPossibilityByStage(request,response);

        }

    }

    private void getPossibilityByStage(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据阶段取得可能性");

        //key
        String stage = request.getParameter("stage");

        //pMap
        ServletContext application = this.getServletContext();

        Map<String,String> pMap = (Map<String,String>)application.getAttribute("pMap");

        //value
        String possibility = pMap.get(stage);

        Map<String,String> map = new HashMap<>();
        map.put("possibility", possibility);

        PrintJson.printJsonObj(response, map);

    }

    private void getContactsListByName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据联系人名称关键字，搜索联系人信息列表");

        String fullname = request.getParameter("cname");

        TransactionServise cs = (TransactionServise) ServiceFactory.getService(new TransactionServiseImpl());

        List<Contacts> cList = cs.getContactsListByName(fullname);

        PrintJson.printJsonObj(response, cList);

    }

    private void getCustomerNameListByName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据客户名称关键字，搜索客户名称列表");

        String name = request.getParameter("name");

        TransactionServise cs = (TransactionServise) ServiceFactory.getService(new TransactionServiseImpl());

        List<String> sList = cs.getCustomerNameListByName(name);

        PrintJson.printJsonObj(response, sList);

    }

    private void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        System.out.println("执行跳转到交易添加页的操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        request.setAttribute("uList", uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);

    }
}
