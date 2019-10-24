package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * Author: 王硕
 * 2019/10/24
 */
public interface DicValueDao {
    List<DicValue> getValueListByCode(String code);
}
