package com.bjpowernode.crm.workbench.vo;

import java.util.List;

/**
 * Author: 王硕
 * 2019/10/22
 */
public class PagingVO<T> {
    private int total;
    private List<T> dataList;

    @Override
    public String toString() {
        return "PagingVO{" +
                "total=" + total +
                ", dataList=" + dataList +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
