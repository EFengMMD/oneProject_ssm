package com.efeng.ssm.controller;

import com.efeng.ssm.domain.Activity;
import com.efeng.ssm.domain.Clue;
import com.efeng.ssm.domain.Tran;
import com.efeng.ssm.domain.User;
import com.efeng.ssm.service.ActivityService;
import com.efeng.ssm.service.ClueService;
import com.efeng.ssm.service.UserService;
import com.efeng.utils.DateTimeUtil;
import com.efeng.utils.JsonArray;
import com.efeng.utils.PrintJson;
import com.efeng.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClueController {
    @Autowired
    ClueService clueService;

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    @RequestMapping("/ssm/clue/getClueIndexPage")
    public String getClueIndexPage(){
        return "clueIndex";
    }

    @GetMapping("/ssm/clue/getUserList")
    @ResponseBody
    public String getUserList(HttpServletResponse response){
        List<User> userList = userService.findUserList();
        return JsonArray.ToJsonByList(userList);
    }

    @PostMapping("/ssm/clue/save")
    public void saveClue(Clue clue,
                         HttpServletResponse response){
        String id = UUIDUtil.getUUID();
        String createBy = "老陈";
        String createTime = DateTimeUtil.getSysTime();
        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        boolean result = clueService.saveClue(clue);
        PrintJson.printJsonFlag(response, result);
    }

    @GetMapping("/workbench/clue/clueDetail")
    public String getTransactionDetailPage(@RequestParam("id") String id,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
        Clue clue = clueService.getClueById(id);
        request.setAttribute("clue", clue);
        return "clueDetail";
    }

    @GetMapping("/ssm/clue/unbindRelation")
    public void unbindRelation(@RequestParam("id") String id,
                               HttpServletResponse response){
        boolean result = clueService.unbindClueActivityRelation(id);
        PrintJson.printJsonFlag(response, result);
    }

    @GetMapping("/ssm/clue/getActivityListRelationNotClueId")
    public void getActivityListRelationNotClueId(String actName, String clueId,
                                                 HttpServletResponse response){
        Map<String,String> map = new HashMap<>();
        map.put("actName", actName);
        map.put("clueId", clueId);
        List<Activity> list = activityService.getActivityListRelationNotClueId(map);
        PrintJson.printJsonObj(response, list);
    }

    @PostMapping("/ssm/clue/bindRelation")
    public void bindRelation(@RequestParam("clueId") String clueId,
                             @RequestParam("activityId") String[] activityIds,
                             HttpServletResponse response){
        boolean result = clueService.saveBindRelation(clueId, activityIds);
        PrintJson.printJsonFlag(response, result);

    }

    @GetMapping("/ssm/clue/getClueConvertPage")
    public ModelAndView getClueConvertPage(String id){
        ModelAndView mav = new ModelAndView();
        Clue clue = clueService.getClueById(id);
        mav.addObject("clue", clue);
        mav.setViewName("clueConvert");
        return mav;
    }


    @PostMapping("/ssm/clue/getActivityListDoSearch")
    @ResponseBody
    public String getActivityListDoSearch(@RequestParam("actName") String actName){
        List<Activity> list = activityService.getActivityListToSearch(actName);
        String json = JsonArray.ToJsonByList(list);
        return json;
    }

    @GetMapping("/ssm/clue/convertNoCheck")
    public ModelAndView convertNoCheck(@RequestParam("clueId")String clueId, Tran tran){
        ModelAndView mav = new ModelAndView();
        boolean result = clueService.convert(tran, clueId, "老陈");
        mav.addObject("success", result);
        mav.setViewName("clueIndex");

        return mav;
    }

    @PostMapping("/ssm/clue/convertHaveCheck")
    public ModelAndView convertHaveCheck(Tran tran, @RequestParam("clueId") String clueId){
        ModelAndView mav = new ModelAndView();
        boolean result = clueService.convert(tran, clueId, "老陈");
        mav.addObject("success", result);
        mav.setViewName("clueIndex");

        return mav;
    }

}
