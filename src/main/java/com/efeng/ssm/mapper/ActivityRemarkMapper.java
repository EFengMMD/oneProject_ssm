package com.efeng.ssm.mapper;

import com.efeng.ssm.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    int getSelectRemarkById(String[] ids);

    int getDeleteRemarkById(String[] ids);

    List<ActivityRemark> getRemarkListById(String activityId);

    boolean deleteRemarkById(String id);

    boolean insertRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    ActivityRemark getRemarkById(ActivityRemark activityRemark);
}
