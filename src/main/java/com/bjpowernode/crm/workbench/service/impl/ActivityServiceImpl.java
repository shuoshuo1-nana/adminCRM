package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.vo.PagingVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/21
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao adao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ActivityRemarkDao ActivityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public boolean addActivity(Activity act) {
        System.out.println("走到这里33333333333");
        boolean flag = false;
        int count = adao.addActivity(act);
        if (count == 1) {
            flag = true;
        }
        return flag;
    }

    @Override
    public PagingVO<Activity> selectActivity(Map<String, Object> map) {
//        //获取activity 内容
//        List<Activity> alist= adao.selectActivity(map);
//        //获取total 数量
//        int total =adao.selectActivityCount(map);
//        PagingVO<Activity>  pagingVO =new PagingVO<>();
//        pagingVO.setTotal(total);
//        pagingVO.setDataList(alist);
//        return pagingVO;
        //取得市场活动列表
        List<Activity> dataList = adao.selectActivity(map);

        //取得总条数
        int total = adao.selectActivityCount(map);

        //创建一个vo对象，将以上查询得到的列表和总条数封装到vo对象中
        PagingVO<Activity> vo = new PagingVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Map<String, Object> showActivity(String id) {
        //获取user姓名
        List<User> ulist = userDao.getUsers();
        //获取活动信息
        Activity activity = adao.showActivity(id);
        Map<String, Object> map = new HashMap<>();
        map.put("name", ulist);
        map.put("activity", activity);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = false;
        int count = adao.update(a);
        if (count == 1) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = false;
        int count = 0;
        count = adao.delete(ids);
        count = ActivityRemarkDao.delete(ids);
        if (count == 2) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity a = adao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> showRemark(String id) {
        List<ActivityRemark> arlist = ActivityRemarkDao.showRemark(id);
        return arlist;
    }

    @Override
    public boolean addRemark(ActivityRemark ar) {
        boolean flag = false;
        int count = ActivityRemarkDao.addRemark(ar);
        if (count == 1) {
            flag = true;
        }
        return false;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = false;
        int count = ActivityRemarkDao.deleteRemark(id);
        if (count == 1) {
            flag = true;
        }
        return false;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = false;
        int count = ActivityRemarkDao.updateRemark(ar);
        if (count == 1) {
            flag = true;
        }
        return false;

    }
}
