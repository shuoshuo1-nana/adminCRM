package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;

/**
 * Author: 王硕
 * 2019/10/25
 */
public interface ClueService {
    boolean addClue(Clue clue);

    Clue showClue(String id);
}
