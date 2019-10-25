package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;

public interface ClueDao {


    int addClue(Clue clue);

    Clue showClue(String id);
}
