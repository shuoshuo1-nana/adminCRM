package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/21
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao adao =  SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    @Override
    public boolean addActivity(Activity act) {
        System.out.println("走到这里33333333333");
        boolean flag =false;
        int count =adao.addActivity(act);
        if (count == 1){
            flag = true;
        }
        return flag;
    }
}
