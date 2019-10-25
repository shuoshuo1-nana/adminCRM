package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;

/**
 * Author: 王硕
 * 2019/10/25
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao =(ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
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
}
