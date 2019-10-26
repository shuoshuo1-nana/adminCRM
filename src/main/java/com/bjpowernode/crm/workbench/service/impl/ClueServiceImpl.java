package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

/**
 * Author: 王硕
 * 2019/10/25
 */
public class ClueServiceImpl implements ClueService {
    //线索
    private ClueDao clueDao = (ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueRemarkDao clueRemarkDao = (ClueRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = (ClueActivityRelationDao) SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    //联系人
    private ContactsDao contactsDao = (ContactsDao) SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = (ContactsActivityRelationDao) SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    private ContactsRemarkDao contactsRemarkDao = (ContactsRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    //客户
    private CustomerDao customerDao = (CustomerDao) SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = (CustomerRemarkDao) SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    //交易
    private TranDao tranDao = (TranDao) SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    //交易备份
    private TranHistoryDao tranHistoryDao = (TranHistoryDao) SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


    @Override
    public boolean addClue(Clue clue) {
        boolean flag = false;
        int count = clueDao.addClue(clue);
        if (count == 1) {
            flag = true;
        }
        return flag;
    }

    @Override
    public Clue showClue(String id) {
        Clue clue = clueDao.showClue(id);
        return clue;
    }

    @Override
    public List<Activity> showActivityRelation(String id) {

        List<Activity> alist = clueActivityRelationDao.showActivityRelation(id);
        return alist;
    }

    @Override
    public List<Activity> showSurplusActivityRelation(String id) {
        List<Activity> alist = clueActivityRelationDao.showSurplusActivityRelation(id);
        return alist;
    }

    @Override
    public boolean unbund(Map<String, Object> map) {
        boolean flag = false;
        int count = clueActivityRelationDao.unbund(map);
        if (count == 1) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByNameNotByClueId(Map<String, Object> map) {
        List<Activity> alist = clueActivityRelationDao.getActivityListByNameNotByClueId(map);
        return alist;
    }

    @Override
    public List<Activity> convertShowContactsActivityRelation(String id) {
        List<Activity> alist = clueActivityRelationDao.showActivityRelation(id);
        return alist;
    }

    @Override
    public List<Activity> convertSelectSurplusActivityRelation(Map<String, Object> map) {
        List<Activity> alist = clueActivityRelationDao.convertSelectSurplusActivityRelation(map);
        return alist;
    }

    @Override
    public boolean convertConversionClient(String clueId, String createBy, Tran t) {
        boolean flag = true;
        // 通过 clueid 获取  转换客户信息
        Clue c = clueDao.showClueByOriginal(clueId);
        //通过 线索公司名称 查询是否有这个客户如果没有新建一个客户
        String company = c.getCompany();
        Customer cus = customerDao.showCustomerById(company);
        if (cus == null) {
            cus = new Customer();
            cus.setWebsite(c.getWebsite());
            cus.setPhone(c.getPhone());
            cus.setOwner(c.getOwner());
            cus.setNextContactTime(c.getNextContactTime());
            cus.setName(c.getCompany());
            cus.setDescription(c.getDescription());
            cus.setContactSummary(c.getContactSummary());
            cus.setAddress(c.getAddress());
            cus.setId(UUIDUtil.getUUID());
            cus.setCreateBy(createBy);
            cus.setCreateTime(DateTimeUtil.getSysTime());
            int count1 = customerDao.insert(cus);
            if (count1 != 1) {
                flag = false;
            }
        }
        //生成一条联系人
        Contacts con = new Contacts();
        con.setSource(c.getSource());
        con.setOwner(c.getOwner());
        con.setNextContactTime(c.getNextContactTime());
        con.setMphone(c.getMphone());
        con.setJob(c.getJob());
        con.setId(UUIDUtil.getUUID());
        con.setFullname(c.getFullname());
        con.setEmail(c.getEmail());
        con.setDescription(c.getDescription());
        con.setCustomerId(cus.getId());
        con.setCreateTime(DateTimeUtil.getSysTime());
        con.setCreateBy(c.getCreateBy());
        con.setContactSummary(c.getContactSummary());
        con.setAppellation(c.getAppellation());
        con.setAddress(c.getAddress());
        int count2 = contactsDao.insert(con);
        if (count2 != 1) {
            flag = false;
        }
        //把对应客户线索备注，放到客户备注（备份）里面
        List<ClueRemark> clueRemarkList = clueRemarkDao.showclueRemarkList(clueId);

        for (ClueRemark crk : clueRemarkList) {
            CustomerRemark ctr = new CustomerRemark();
            ctr.setId(UUIDUtil.getUUID());
            ctr.setEditFlag("0");
            ctr.setCustomerId(cus.getId());
            ctr.setCreateTime(DateTimeUtil.getSysTime());
            ctr.setCreateBy(createBy);
            ctr.setNoteContent(crk.getNoteContent());
            int count3 = customerRemarkDao.insert(ctr);
            if (count3 != 1) {
                flag = false;
            }
            //生成一条联系人备注来保存联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setNoteContent(crk.getNoteContent());
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setEditFlag("0");
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setContactsId(con.getId());
            int count4 = contactsRemarkDao.insert(contactsRemark);
            if (count4 != 1) {
                flag = false;
            }
        }
        //将该线索与市场活动的关联关系 转换（备份） 到联系人和市场活动的关联关系上
        List<ClueActivityRelation> slist = clueActivityRelationDao.showActivityRelationById(clueId);
        for (ClueActivityRelation car : slist) {
            ContactsActivityRelation ctr = new ContactsActivityRelation();
            ctr.setActivityId(car.getActivityId());
            ctr.setId(UUIDUtil.getUUID());
            ctr.setContactsId(con.getId());
            int count5 = contactsActivityRelationDao.insert(ctr);
            if (count5 != 1) {
                flag = false;
            }
        }
        //判断T不等于null那就说明已经有交易发生
//       id,name,money,expectedDate,stage,activityId,createBy,createTime
        if (t != null) {
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(cus.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(con.getId());
            int count6 = tranDao.insert(t);
            if (count6 != 1) {
                flag = false;
            }
            //如果创建了一条交易那就要生成一条交易历史
            TranHistory th = new TranHistory();
            th.setCreateBy(createBy);
            th.setTranId(t.getId());
            th.setStage(t.getStage());
            th.setMoney(t.getMoney());
            th.setId(UUIDUtil.getUUID());
            th.setExpectedDate(t.getExpectedDate());
            th.setCreateTime(DateTimeUtil.getSysTime());

            int count7 = tranHistoryDao.insert(th);
            if (count7 != 1) {
                flag = false;
            }
        }

        //删除线索关联的备注信息
        for (ClueRemark clueRemark : clueRemarkList) {
          int count8 =clueRemarkDao.delete(clueRemark);
            if (count8 != 1) {
                flag = false;
            }
        }
        //删除线索市场活动关联表
        for (ClueActivityRelation clueActivityRelation : slist) {
            int count9 =clueActivityRelationDao.delete(clueActivityRelation);
            if (count9 != 1) {
                flag = false;
            }
        }
        //删除线索表
        int count10 =clueDao.delete(clueId);
        if (count10 != 1) {
            flag = false;
        }

        return flag;
    }
}
