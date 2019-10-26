package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationDao {


    List<Activity> showActivityRelation(String id);

    List<Activity> showSurplusActivityRelation(String id);

    int unbund(Map<String,Object> map);

    List<Activity> getActivityListByNameNotByClueId(Map<String,Object> map);

    List<Activity> convertSelectSurplusActivityRelation(Map<String, Object> map);

    List<ClueActivityRelation> showActivityRelationById(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
