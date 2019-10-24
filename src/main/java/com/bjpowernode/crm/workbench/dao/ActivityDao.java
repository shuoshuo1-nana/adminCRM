package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * 王硕
 * 2019/10/20
 */
public interface ActivityDao {


    int addActivity(Activity act);

    int selectActivityCount(Map<String, Object> map);

    List<Activity> selectActivity(Map<String, Object> map);

    Activity showActivity(String id);

    int update(Activity a);

    int delete(String[] ids);

    Activity detail(String id);
}
