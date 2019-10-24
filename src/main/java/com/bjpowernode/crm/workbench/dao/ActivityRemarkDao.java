package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * 王硕
 * 2019/10/20
 */
public interface ActivityRemarkDao {

    int delete(String[] ids);

    List<ActivityRemark> showRemark(String id);

    int addRemark(ActivityRemark ar);

    int deleteRemark(String id);

    int updateRemark(ActivityRemark ar);

}
