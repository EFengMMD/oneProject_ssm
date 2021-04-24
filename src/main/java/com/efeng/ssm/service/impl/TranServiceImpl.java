package com.efeng.ssm.service.impl;

import com.efeng.ssm.domain.Customer;
import com.efeng.ssm.domain.Tran;
import com.efeng.ssm.domain.TranHistory;
import com.efeng.ssm.mapper.CustomerMapper;
import com.efeng.ssm.mapper.TranHistoryMapper;
import com.efeng.ssm.mapper.TranMapper;
import com.efeng.ssm.service.TranService;
import com.efeng.utils.DateTimeUtil;
import com.efeng.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    TranMapper tranMapper;
    @Autowired
    TranHistoryMapper tranHistoryMapper;
    @Autowired
    CustomerMapper customerMapper;

    @Override
    public boolean addTran(Tran tran, String customerName) {
        boolean flag = true;

        //获取客户id，如果有则获取，没有则创建
        Customer customer = customerMapper.getCustomerByName(customerName);
        if (customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setDescription(tran.getDescription());
            customer.setNextContactTime(tran.getNextContactTime());
            flag = customerMapper.insertCustomer(customer);
        }
        tran.setCustomerId(customer.getId());
        flag = tranMapper.saveTran(tran);

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());

        flag = tranHistoryMapper.saveTranHistory(tranHistory);


        return flag;
    }

    @Override
    public Tran getTranById(String id) {
        return tranMapper.findTranById(id);
    }

    @Override
    public List<TranHistory> getTranHistory(String tranId) {
        return tranHistoryMapper.findTranHistoryListByTranId(tranId);
    }

    @Override
    public boolean updateTran(Tran tran) {
        boolean flag = true;

        flag = tranMapper.editTran(tran);

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy("老高");
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        flag = tranHistoryMapper.saveTranHistory(tranHistory);

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        int total = tranMapper.getTotal();
        List<Map<String, String>> dataList = tranMapper.getDataList();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("dataList", dataList);
        return map;
    }
}
