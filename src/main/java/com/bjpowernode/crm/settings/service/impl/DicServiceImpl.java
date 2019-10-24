package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/24
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao =(DicTypeDao) SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao =(DicValueDao) SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();

        //查询得到字典类型列表
        List<DicType> dtList = dicTypeDao.getTypeList();

        //遍历类型列表
        for(DicType dt:dtList){

            //取得每一个子字典类型编码
            String code = dt.getCode();

            //根据每一种字典类型，查询相应的字典值列表
            List<DicValue> dvList = dicValueDao.getValueListByCode(code);

            map.put(code+"List", dvList);

        }

        return map;
    }
}
