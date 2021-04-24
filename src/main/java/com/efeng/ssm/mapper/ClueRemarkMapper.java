package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {

    List<ClueRemark> selectClueRemark(String clueId);

    boolean removeClueRemarkByClueId(String clueId);

}
