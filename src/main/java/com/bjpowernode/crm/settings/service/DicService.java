package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/24
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();

}
