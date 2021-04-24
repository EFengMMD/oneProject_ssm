package com.efeng.ssm.service.impl;

import com.efeng.ssm.domain.Activity;
import com.efeng.ssm.domain.ActivityRemark;
import com.efeng.ssm.domain.User;
import com.efeng.ssm.mapper.ActivityMapper;
import com.efeng.ssm.mapper.ActivityRemarkMapper;
import com.efeng.ssm.mapper.UserMapper;
import com.efeng.ssm.service.ActivityService;
import com.efeng.ssm.vo.PaginationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    ActivityRemarkMapper activityRemarkMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean saveActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        List<Activity> list = activityMapper.getActivityListByCondition(map);
        Integer total = activityMapper.getTotalByCondition(map);
        PaginationVo<Activity> pv = new PaginationVo<>();
        pv.setDataList(list);
        pv.setTotal(total);
        return pv;
    }

    @Override
    public boolean getDeleteActivityResult(String[] ids) {
        System.out.println("测试是否被调用");
        boolean result = true;
        int selectCount = activityRemarkMapper.getSelectRemarkById(ids);

        int delCount = activityRemarkMapper.getDeleteRemarkById(ids);

        if (selectCount != delCount){
            result = false;
        }

        int delActivityByIdCount = activityMapper.delActivityById(ids);

        if (delActivityByIdCount != ids.length){
            result = false;
        }

        return result;
    }

    public Map<String, Object> getUserListAndActivity(String id){
        List<User> userList = userMapper.getUserList();
        Activity act = activityMapper.getById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("act", act);
        return map;
    }

    @Override
    public boolean editActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public boolean deleteRemark(String id) {
        return activityRemarkMapper.deleteRemarkById(id);
    }

    @Override
    public List<ActivityRemark> getRemarkListById(String activityId) {
        return activityRemarkMapper.getRemarkListById(activityId);
    }

    @Override
    public Activity detailGetActivity(String id) {
        return activityMapper.detailGetActivity(id);
    }

    @Override
    public boolean insertRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertRemark(activityRemark);
    }

    @Override
    public Map<String, Object> updateRemark(ActivityRemark activityRemark) {
        Map<String, Object> map = new HashMap<>();
        boolean result = activityRemarkMapper.updateRemark(activityRemark);
        ActivityRemark remark = activityRemarkMapper.getRemarkById(activityRemark);
        map.put("success", result);
        map.put("act", remark);
        return map;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {

        return activityMapper.getActivityListByClueId(clueId);
    }

    @Override
    public List<Activity> getActivityListRelationNotClueId(Map<String, String> map) {
        return activityMapper.getActivityListRelationByClue(map);
    }

    @Override
    public List<Activity> getActivityListToSearch(String actName) {
        return activityMapper.getActivityListToSearch(actName);
    }
}
