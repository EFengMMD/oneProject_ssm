package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationMapper {
    boolean delClueActivityRelation(String id);

    boolean insertBindRelation(ClueActivityRelation car);

    List<ClueActivityRelation> findClueActivityRelationByClueId(String clueId);
}
