package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.vo.PagingVO;

import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/21
 */
public interface ActivityService {

    boolean addActivity(Activity act);

    PagingVO<Activity> selectActivity(Map<String, Object> map);

    Map<String, Object> showActivity(String id);

    boolean update(Activity a);

    boolean delete(String[] ids);

    Activity detail(String id);

    List<ActivityRemark> showRemark(String id);

    boolean addRemark(ActivityRemark ar);

    boolean deleteRemark(String id);

}
