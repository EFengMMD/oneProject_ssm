package com.efeng.ssm.service;

import com.efeng.ssm.domain.Clue;
import com.efeng.ssm.domain.Tran;

import java.util.Map;

public interface ClueService {

    boolean saveClue(Clue clue);

    Clue getClueById(String clueId);

    boolean unbindClueActivityRelation(String id);

    boolean saveBindRelation(String clueId, String[] activityIds);

    boolean convert(Tran tran, String clueId, String createBy);
}
