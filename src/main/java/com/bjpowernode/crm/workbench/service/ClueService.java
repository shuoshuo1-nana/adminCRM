package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/25
 */
public interface ClueService {
    boolean addClue(Clue clue);

    Clue showClue(String id);

    List<Activity> showActivityRelation(String id);

    List<Activity> showSurplusActivityRelation(String id);

    boolean unbund(Map<String,Object> map);

    List<Activity> getActivityListByNameNotByClueId(Map<String,Object> map);

    List<Activity> convertShowContactsActivityRelation(String id);

    List<Activity> convertSelectSurplusActivityRelation(Map<String, Object> map);

    boolean convertConversionClient(String clueid, String createBy, Tran t);
}
