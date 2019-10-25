package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

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
 * 2019/10/25
 */
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        String path = requset.getServletPath();
        if ("/workbench/activity/addClue.do".equals(path)){
            addClue(requset, response);
        }else if("/workbench/activity/showClue.do".equals(path)){
            showClue(requset, response);
        }else if("/workbench/activity/showActivityRelation.do".equals(path)){
            showActivityRelation(requset, response);
        }else if("/workbench/activity/showSurplusActivityRelation.do".equals(path)){
            showSurplusActivityRelation(requset, response);
        }else if("/workbench/activity/unbund.do".equals(path)){
            unbund(requset, response);
        }else if("/workbench/activity/getActivityListByNameNotByClueId.do".equals(path)){
            getActivityListByNameNotByClueId(requset, response);
        }else if("/workbench/activity/convertShowContactsActivityRelation.do".equals(path)){
            convertShowContactsActivityRelation(requset, response);
        }else if("/workbench/activity/convertSelectSurplusActivityRelation.do".equals(path)){
            convertSelectSurplusActivityRelation(requset, response);
        }
    }

    private void convertSelectSurplusActivityRelation(HttpServletRequest requset, HttpServletResponse response) {
        String name =requset.getParameter("name");
        String id =requset.getParameter("id");
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Map<String,Object>  map =new HashMap<>();
        map.put("id", id);
        map.put("name",name);
        List<Activity> alist =cs.convertSelectSurplusActivityRelation(map);
        PrintJson.printJsonObj(response, alist);
    }

    private void convertShowContactsActivityRelation(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("查询转换线索关联信息");
        String id =requset.getParameter("id");
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> alist =cs.convertShowContactsActivityRelation(id);
        PrintJson.printJsonObj(response, alist);
    }

    private void getActivityListByNameNotByClueId(HttpServletRequest requset, HttpServletResponse response) {
        String name =requset.getParameter("name");
        String id =requset.getParameter("id");
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Map<String,Object>  map =new HashMap<>();
        map.put("id", id);
        map.put("name",name);
        List<Activity> alist =cs.getActivityListByNameNotByClueId(map);
        PrintJson.printJsonObj(response, alist);
    }

    private void unbund(HttpServletRequest requset, HttpServletResponse response) {
        System.out.println("解除关联");
        String activityId =requset.getParameter("activityId");
        String clueId =requset.getParameter("clueId");
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Map<String,Object>  map =new HashMap<>();
        map.put("activityId", activityId);
        map.put("clueId",clueId);
        boolean flag =cs.unbund(map);
        PrintJson.printJsonFlag(response, flag);
    }

    private void showSurplusActivityRelation(HttpServletRequest requset, HttpServletResponse response) {
        String id =requset.getParameter("id");
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> alist =cs.showSurplusActivityRelation(id);
        PrintJson.printJsonObj(response, alist);
    }

    private void showActivityRelation(HttpServletRequest requset, HttpServletResponse response) {
        String id =requset.getParameter("id");
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        List<Activity> alist =cs.showActivityRelation(id);
        PrintJson.printJsonObj(response, alist);
    }

    private void showClue(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("显示线索详情");
        String id =requset.getParameter("id");
        Clue clue = new Clue();
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        clue =cs.showClue(id);
        requset.setAttribute("clue", clue);
        requset.getRequestDispatcher("/workbench/clue/detail.jsp").forward(requset, response);
    }

    private void addClue(HttpServletRequest requset, HttpServletResponse response) {
        String id = UUIDUtil.getUUID();
        String fullname = requset.getParameter("fullname");
        String appellation = requset.getParameter("appellation");
        String owner = requset.getParameter("owner");
        String company = requset.getParameter("company");
        String job = requset.getParameter("job");
        String email = requset.getParameter("email");
        String phone = requset.getParameter("phone");
        String website = requset.getParameter("website");
        String mphone = requset.getParameter("mphone");
        String state = requset.getParameter("state");
        String source = requset.getParameter("source");
        String createBy = ((User) requset.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = requset.getParameter("description");
        String contactSummary = requset.getParameter("contactSummary");
        String nextContactTime = requset.getParameter("nextContactTime");
        String address = requset.getParameter("address");
        Clue clue = new Clue();
        clue.setAddress(address);
        clue.setWebsite(website);
        clue.setState(state);
        clue.setSource(source);
        clue.setPhone(phone);
        clue.setOwner(owner);
        clue.setNextContactTime(nextContactTime);
        clue.setMphone(mphone);
        clue.setJob(job);
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setCompany(company);
        clue.setEmail(email);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        ClueService  cs =(ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean  flag =cs.addClue(clue);
        PrintJson.printJsonFlag(response, flag);

    }
}

