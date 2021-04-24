package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {

    boolean saveTran(Tran tran);

    Tran findTranById(String id);

    boolean editTran(Tran tran);

    int getTotal();

    List<Map<String, String>> getDataList();

}
