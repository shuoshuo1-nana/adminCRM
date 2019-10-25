package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;

/**
 * Author: 王硕
 * 2019/10/25
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao =(ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao =(ClueActivityRelationDao) SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    @Override
    public boolean addClue(Clue clue) {
        boolean flag =false;
        int count =clueDao.addClue(clue);
        if (count==1){
            flag =true;
        }
        return flag;
    }

    @Override
    public Clue showClue(String id) {
        Clue clue=clueDao.showClue(id);
        return clue;
    }

    @Override
    public List<Activity> showActivityRelation(String id) {

        List<Activity> alist =clueActivityRelationDao.showActivityRelation(id);
        return alist;
    }

    @Override
    public List<Activity> showSurplusActivityRelation(String id) {
        List<Activity> alist =clueActivityRelationDao.showSurplusActivityRelation(id);
        return alist;
    }
}
