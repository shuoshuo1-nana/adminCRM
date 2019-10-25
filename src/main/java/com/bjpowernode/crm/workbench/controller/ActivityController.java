package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.vo.PagingVO;
import sun.misc.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/21
 */
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        String path = requset.getServletPath();
        if ("/workbench/Activity/Activity.do".equals(path)) {
            selectUser(requset, response);
        } else if ("/workbench/Activity/AddActivity.do".equals(path)) {
            addActivity(requset, response);
        } else if ("/workbench/Activity/selectActivity.do".equals(path)) {
            selectActivity(requset, response);
        } else if ("/workbench/Activity/showActivity.do".equals(path)) {
            showActivity(requset, response);
        } else if ("/workbench/Activity/update.do".equals(path)) {
            update(requset, response);
        } else if ("/workbench/Activity/delete.do".equals(path)) {
            delete(requset, response);
        } else if ("/workbench/activity/detail.do".equals(path)) {
            System.out.println("显示市场信息详情");
            detail(requset, response);
        } else if ("/workbench/activity/showRemark.do".equals(path)) {
            System.out.println("显示市场信息详情");
            showRemark(requset, response);
        } else if ("/workbench/activity/addRemark.do".equals(path)) {
            System.out.println("显示市场信息详情");
            addRemark(requset, response);
        } else if ("/workbench/activity/deleteRemark.do".equals(path)) {
            System.out.println("删除市场信息详情");
            deleteRemark(requset, response);
        } else if ("/workbench/activity/updateRemark.do".equals(path)) {
            System.out.println("修改市场信息详情");
            updateRemark(requset, response);
        } else if ("/workbench/activity/showUserName.do".equals(path)) {
            System.out.println("显示User名称详情");
            selectUser(requset, response);
        }
    }

    private void updateRemark(HttpServletRequest requset, HttpServletResponse response) {
        //获取浏览器信息
        String id = requset.getParameter("id");
        String noteContent = requset.getParameter("noteContent");
//        String activityId = requset.getParameter("activityId");
        //获取内部信息
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) requset.getSession().getAttribute("user")).getName();
        ActivityRemark ar = new ActivityRemark();
        ar.setEditFlag("1");
//        ar.setActivityId(activityId);
        ar.setId(id);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ar.setNoteContent(noteContent);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> arr = as.updateRemark(ar);
        PrintJson.printJsonObj(response, arr);
    }

    private void deleteRemark(HttpServletRequest requset, HttpServletResponse response) {
        String id = requset.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.deleteRemark(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void addRemark(HttpServletRequest requset, HttpServletResponse response) {
        String activityId = requset.getParameter("id");
        String noteContent = requset.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) requset.getSession().getAttribute("user")).getName();

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setActivityId(activityId);
        ar.setEditFlag("0");


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = as.addRemark(ar);

        PrintJson.printJsonObj(response, map);
    }

    private void showRemark(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("显示到了备注详情");
        String id = requset.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arlist = as.showRemark(id);
        PrintJson.printJsonObj(response, arlist);
    }

    private void detail(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("展现活动信息详情");
        String id = requset.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = as.detail(id);
        requset.setAttribute("a", a);
        requset.getRequestDispatcher("/workbench/activity/detail.jsp").forward(requset, response);
    }

    private void delete(HttpServletRequest requset, HttpServletResponse response) {
        String[] ids = requset.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response, flag);
    }

    private void update(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("更新市场活动信息");
        //获取员工信息

        /*
            private String id;  //编号
            private String owner;   //所有者 外键 关联tbl_user表 该字段需要保存的信息是用户的id（不是名字！！！！！！）
            private String name;    //市场活动名称
            private String startDate;   //开始日期 yyyy-MM-dd 10
            private String endDate; //结束日期 yyyy-MM-dd 10
            private String cost;    //成本
            private String description; //描述
            private String createTime;  //创建时间
            private String createBy;    //创建人
            private String editTime;    //修改时间
            private String editBy;  //修改人
        *
        * */
        String id = requset.getParameter("id");
        String owner = requset.getParameter("owner");
        String name = requset.getParameter("name");
        String startDate = requset.getParameter("startDate");
        String endDate = requset.getParameter("edit-endDate");
        String cost = requset.getParameter("cost");
        String description = requset.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) requset.getSession().getAttribute("user")).getName();
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.update(a);
        PrintJson.printJsonFlag(response, flag);
    }

    //显示市场活动
    private void showActivity(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("获取活动列表信息！！！！");
        String id = requset.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = new HashMap<>();
        map = as.showActivity(id);
        PrintJson.printJsonObj(response, map);
    }


    //分页查询
    private void selectActivity(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("分页查询");
        //获取信息
        String name = requset.getParameter("name");
        String owner = requset.getParameter("owner");
        String startDate = requset.getParameter("startDate");
        String endDate = requset.getParameter("endDate");
        //页码
        String pageNoStr = requset.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        //每页显示数量
        String pageSizeStr = requset.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        //获得当前获得当前页面跳过条数
        int skipCount = (pageNo - 1) * pageSize;
        //创建Map集合填入信息
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);
        //创建PagingVO对象
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PagingVO<Activity> pm = as.selectActivity(map);
        PrintJson.printJsonObj(response, pm);

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
        String createBy = ((User) requset.getSession().getAttribute("user")).getName();
        Activity ad = new Activity();
        ad.setId(id);
        ad.setOwner(owner);
        ad.setName(name);
        ad.setStartDate(startDate);
        ad.setEndDate(endDate);
        ad.setCost(cost);
        ad.setDescription(description);
        ad.setCreateTime(createTime);
        ad.setCreateBy(createBy);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.addActivity(ad);
        PrintJson.printJsonFlag(response, flag);
    }

    private void selectUser(HttpServletRequest requset, HttpServletResponse response) {
        List<User> slist = new ArrayList<>();
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        slist = userService.getUsers();
        PrintJson.printJsonObj(response, slist);
    }
}
