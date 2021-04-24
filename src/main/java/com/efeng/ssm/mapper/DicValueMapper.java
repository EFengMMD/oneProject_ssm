package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.DicValue;

import java.util.List;

public interface DicValueMapper {

    List<DicValue> getDicValueList(String code);
}
