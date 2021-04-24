package com.efeng.ssm.controller;

import com.efeng.ssm.domain.Activity;
import com.efeng.ssm.domain.ActivityRemark;
import com.efeng.ssm.domain.User;
import com.efeng.ssm.service.ActivityService;
import com.efeng.ssm.service.UserService;
import com.efeng.ssm.vo.PaginationVo;
import com.efeng.utils.DateTimeUtil;
import com.efeng.utils.JsonArray;
import com.efeng.utils.PrintJson;
import com.efeng.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    @RequestMapping("/ssm/activity/index")
    public String indexPage(){
        return "activityIndex";
    }

    @RequestMapping("/ssm/activity/detail")
    public String detailPage(@RequestParam("id") String id,
                             HttpServletRequest request){
        Activity activity = activityService.detailGetActivity(id);
        request.setAttribute("activity", activity);

        return "activityDetail";
    }


    @RequestMapping(value = "/ssm/activity/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public String getUserList(){
        List<User> userList = userService.findUserList();
        String jsonByList = JsonArray.ToJsonByList(userList);
        return jsonByList;
    }

    @RequestMapping(value = "/ssm/activity/insert", method=RequestMethod.POST)
    public void insertActivity(Activity activity,
                               HttpServletRequest request,
                               HttpServletResponse response){
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = "王五";
        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        boolean flag = activityService.saveActivity(activity);
        PrintJson.printJsonFlag(response, flag);

    }

    @RequestMapping(value = "/ssm/activity/pageList", method = RequestMethod.GET)
    public void searchList(@RequestParam("pageNo") String pageNo,
                           @RequestParam("pageSize") String pageSize,
                           Activity activity,
                           HttpServletResponse response){
        int pageNoInt = Integer.valueOf(pageNo);
        int pageSizeInt = Integer.valueOf(pageSize);
        int skipCount = (pageNoInt - 1) * pageSizeInt;
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNoInt);
        map.put("pageSize", pageSizeInt);
        map.put("skipCount", skipCount);
        map.put("name", activity.getName());
        map.put("owner", activity.getOwner());
        map.put("startDate", activity.getStartDate());
        map.put("endDate", activity.getEndDate());

        PaginationVo<Activity> vo = activityService.pageList(map);
        PrintJson.printJsonObj(response, vo);
    }

    @RequestMapping(value = "/ssm/activity/delete", method = RequestMethod.POST)
    public void deleteList(@RequestParam("id") String[] ids,
                           HttpServletResponse response){
        boolean result = activityService.getDeleteActivityResult(ids);
        PrintJson.printJsonFlag(response, result);
    }

    @RequestMapping(value = "/ssm/activity/getUserListAndUpdate", method = RequestMethod.POST)
    public void getUserAndActivity(@RequestParam("id") String id,
                                   HttpServletResponse response){
        Map<String, Object> map = activityService.getUserListAndActivity(id);
        PrintJson.printJsonObj(response, map);
    }

    @RequestMapping(value = "/ssm/activity/update", method = RequestMethod.POST)
    public void updateActivity(Activity activity,
                               HttpServletResponse response){
        boolean result = activityService.editActivity(activity);
        PrintJson.printJsonFlag(response, result);
    }

    @RequestMapping(value = "/ssm/activityRemark/getRemarkListByAid", method = RequestMethod.GET)
    public void getRemarkListByAid(@RequestParam("activityId") String aid,
                                   HttpServletResponse response){
        List<ActivityRemark> list = activityService.getRemarkListById(aid);
        PrintJson.printJsonObj(response, list);

    }

    @GetMapping("/ssm/activity/deleteRemark")
    public void removeRemark(@RequestParam("id") String id, HttpServletResponse response){
        boolean remark = activityService.deleteRemark(id);
        System.out.println("result=" +remark);
        PrintJson.printJsonFlag(response, remark);
    }

    @PostMapping("/ssm/activity/saveRemark")
    public void saveRemark(ActivityRemark activityRemark,
                           HttpServletResponse response){
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = "老王";
        String editFlag = "0";
        activityRemark.setId(id);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag(editFlag);
        boolean remark = activityService.insertRemark(activityRemark);
        Map<String, Object> map = new HashMap<>();
        map.put("success", remark);
        map.put("act", activityRemark);
        PrintJson.printJsonObj(response, map);

    }

    @PostMapping("/ssm/activity/editRemark")
    public void editRemark(ActivityRemark activityRemark,
                           HttpServletResponse response){
        String editTime = DateTimeUtil.getSysTime();
        String editBy = "老陈";
        String editFlag = "1";
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag(editFlag);
        Map<String, Object> map = activityService.updateRemark(activityRemark);
        System.out.println("结果是" + map);
        PrintJson.printJsonObj(response, map);
    }

    @GetMapping("/ssm/activity/showActivityToClueDetail")
    @ResponseBody
    public String showActivityToClueDetail(String clueId){
        List<Activity> activityList = activityService.getActivityListByClueId(clueId);
        String jsonByList = JsonArray.ToJsonByList(activityList);
        return jsonByList;
    }
}
