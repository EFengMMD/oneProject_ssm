package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    boolean insertActivity(Activity activity);

    Integer getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int delActivityById(String[] ids);

    Activity getById(String id);

    boolean updateActivity(Activity activity);

    Activity detailGetActivity(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListRelationByClue(Map<String,String> map);

    List<Activity> getActivityListToSearch(String actName);
}
