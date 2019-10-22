package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: 王硕
 * 2019/10/21
 */
public class ActivityController extends HttpServlet {
    private ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

    @Override
    protected void service(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        String path = requset.getServletPath();
        if ("/workbench/Activity/Activity.do".equals(path)){
            selectUser(requset,response);
        }else if ("/workbench/Activity/AddActivity.do".equals(path)){
            addActivity(requset,response);
        }else if ("/workbench/Activity/selectActivity.do".equals(path)){
            selectActivity(requset,response);
        }
    }

    //分页查询
    private void selectActivity(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("分页查询");
    }

    //添加信息
    private void addActivity(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("添加信息，走到这里");
        String id = UUIDUtil.getUUID();
        String owner = requset.getParameter("owner");
        String name = requset.getParameter("name");
        String startDate = requset.getParameter("startDate");
        String endDate = requset.getParameter("endDate");
        String cost = requset.getParameter("cost");
        String description = requset.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)requset.getSession().getAttribute("user")).getName();
        Activity ad =new Activity();
        ad.setId(id);
        ad.setOwner(owner);
        ad.setName(name);
        ad.setStartDate(startDate);
        ad.setEndDate(endDate);
        ad.setCost(cost);
        ad.setDescription(description);
        ad.setCreateTime(createTime);
        ad.setCreateBy(createBy);
        boolean flag =as.addActivity(ad);
        PrintJson.printJsonFlag(response,flag);
    }

    private void selectUser(HttpServletRequest requset, HttpServletResponse response) {
        List<User> slist =new ArrayList<>();
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
       slist =  userService.getUsers();
        PrintJson.printJsonObj(response, slist);
    }
}
