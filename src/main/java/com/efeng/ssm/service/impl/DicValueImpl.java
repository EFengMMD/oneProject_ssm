package com.efeng.ssm.service.impl;

import com.efeng.ssm.domain.DicType;
import com.efeng.ssm.domain.DicValue;
import com.efeng.ssm.mapper.DicTypeMapper;
import com.efeng.ssm.mapper.DicValueMapper;
import com.efeng.ssm.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicValueImpl implements DicValueService {
    @Autowired
    DicTypeMapper typeMapper;

    @Autowired
    DicValueMapper valueMapper;


    @Override
    public Map<String, List<DicValue>> getDicValueList() {
        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();
        List<DicType> typeList = typeMapper.getDicTypeCode();
        for (DicType dicType: typeList){
            String code = dicType.getCode();
            List<DicValue> dicValueList = valueMapper.getDicValueList(code);
            map.put(code+"List", dicValueList);
        }
        return map;
    }
}
