package com.efeng.ssm.service;

import com.efeng.ssm.domain.Tran;
import com.efeng.ssm.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {

    boolean addTran(Tran tran, String customerName);

    Tran getTranById(String id);

    List<TranHistory> getTranHistory(String tranId);

    boolean updateTran(Tran tran);

    Map<String, Object> getCharts();
}
