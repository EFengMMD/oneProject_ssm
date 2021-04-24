package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.TranHistory;

import java.util.List;

public interface TranHistoryMapper {

    boolean saveTranHistory(TranHistory tranHistory);

    List<TranHistory> findTranHistoryListByTranId(String tranId);

}
