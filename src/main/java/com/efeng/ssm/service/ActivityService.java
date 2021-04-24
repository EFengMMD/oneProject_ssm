package com.efeng.ssm.service;

import com.efeng.ssm.domain.Activity;
import com.efeng.ssm.domain.ActivityRemark;
import com.efeng.ssm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean saveActivity(Activity activity);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean getDeleteActivityResult(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean editActivity(Activity activity);

    Activity detailGetActivity(String id);

    List<ActivityRemark> getRemarkListById(String activityId);

    boolean deleteRemark(String id);

    boolean insertRemark(ActivityRemark activityRemark);

    Map<String, Object> updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListRelationNotClueId(Map<String, String> map);

    List<Activity> getActivityListToSearch(String actName);
}
