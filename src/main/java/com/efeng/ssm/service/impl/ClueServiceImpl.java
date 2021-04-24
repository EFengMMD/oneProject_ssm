package com.efeng.ssm.service.impl;

import com.efeng.ssm.domain.*;
import com.efeng.ssm.mapper.*;
import com.efeng.ssm.service.ClueService;
import com.efeng.utils.DateTimeUtil;
import com.efeng.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueMapper clueMapper;

    @Autowired
    ClueActivityRelationMapper carMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ContactsMapper contactsMapper;

    @Resource
    ClueRemarkMapper clueRemarkMapper;

    @Autowired
    CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    TranMapper tranMapper;

    @Autowired
    TranHistoryMapper tranHistoryMapper;

    @Override
    public boolean saveClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue getClueById(String clueId) {
        return clueMapper.getClueById(clueId);
    }

    @Override
    public boolean unbindClueActivityRelation(String id) {
        return carMapper.delClueActivityRelation(id);
    }

    @Override
    public boolean saveBindRelation(String clueId, String[] activityIds) {
        boolean flag = false;
        ClueActivityRelation car = new ClueActivityRelation();
        for (String activityId : activityIds){
            String id = UUIDUtil.getUUID();
            car.setId(id);
            car.setClueId(clueId);
            car.setActivityId(activityId);
            flag = carMapper.insertBindRelation(car);
        }
        return flag;
    }

    @Override
    public boolean convert(Tran tran, String clueId, String createBy) {
        boolean flag = true;
        //获取线索信息
        Clue clue = clueMapper.getClueById(clueId);
        String company = clue.getCompany();
        //通过线索信息去获取客户信息
        Customer customer = customerMapper.getCustomerByName(company);
        if (customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(company);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(clue.getContactSummary());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            flag = customerMapper.insertCustomer(customer);
        }

        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());

        flag = contactsMapper.insertContacts(contacts);

        List<ClueRemark> clueRemarks = clueRemarkMapper.selectClueRemark(clueId);
        for (ClueRemark clueRemark : clueRemarks){
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            flag = customerRemarkMapper.saveCustomerRemark(customerRemark);

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            flag = contactsRemarkMapper.saveContactsRemark(contactsRemark);
        }

        List<ClueActivityRelation> clueActivityRelationList = carMapper.findClueActivityRelationByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());
            flag = contactsActivityRelationMapper.saveContactsActivityRelation(contactsActivityRelation);
        }

        if (tran != null){
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            tran.setSource(clue.getSource());
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(createBy);
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setDescription(clue.getDescription());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());
            flag = tranMapper.saveTran(tran);

            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setTranId(tran.getId());
            flag = tranHistoryMapper.saveTranHistory(tranHistory);
        }

        flag = clueRemarkMapper.removeClueRemarkByClueId(clueId);

        flag = carMapper.delClueActivityRelation(clueId);

        flag = clueMapper.removeClueById(clueId);

        return flag;
    }
}
