package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

/**
 * Author: 王硕
 * 2019/10/25
 */
public interface ClueService {
    boolean addClue(Clue clue);

    Clue showClue(String id);

    List<Activity> showActivityRelation(String id);

    List<Activity> showSurplusActivityRelation(String id);
}
