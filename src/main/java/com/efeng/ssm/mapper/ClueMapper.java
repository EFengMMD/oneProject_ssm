package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.Clue;

public interface ClueMapper {
    boolean insertClue(Clue clue);

    Clue getClueById(String id);

    boolean removeClueById(String id);
}
