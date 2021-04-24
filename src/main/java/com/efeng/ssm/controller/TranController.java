package com.efeng.ssm.controller;

import com.efeng.ssm.domain.Tran;
import com.efeng.ssm.domain.TranHistory;
import com.efeng.ssm.domain.User;
import com.efeng.ssm.service.CustomerService;
import com.efeng.ssm.service.TranService;
import com.efeng.ssm.service.UserService;
import com.efeng.utils.DateTimeUtil;
import com.efeng.utils.JsonArray;
import com.efeng.utils.PrintJson;
import com.efeng.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TranController {
    @Autowired
    TranService tranService;
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;

    @RequestMapping("/ssm/transaction/getSavePage")
    public ModelAndView getSavePage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("transactionSave");
        List<User> userList = userService.findUserList();
        mav.addObject("userList", userList);
        return mav;
    }

//    @RequestMapping("/ssm/transaction/getIndexPage")
//    public String getIndexPage(){
//        return "/workbench/transaction/transactionIndex.jsp";
//    }

    @RequestMapping("/ssm/transaction/getCustomerName")
    public void getCustomerName(@RequestParam("name") String name,
                                HttpServletResponse response){
        List<String> list = customerService.getCustomerName(name);
        PrintJson.printJsonObj(response, list);
    }

    @RequestMapping(value = "/ssm/transaction/saveTran", method = RequestMethod.POST)
    public ModelAndView addTran(Tran tran,
                                @RequestParam("customerName") String customerName,
                                HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateBy("老陈");
        tran.setCreateTime(DateTimeUtil.getSysTime());
        boolean result = tranService.addTran(tran, customerName);
        mav.setViewName("redirect:/workbench/transaction/transactionIndex.jsp");
        return mav;
    }

    @RequestMapping("/ssm/transaction/getDetailPage")
    public ModelAndView getDetailPage(@RequestParam("id") String id,
                                      HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        Tran tran = tranService.getTranById(id);
        ServletContext application = request.getServletContext();
        Map<String, String> pMap = (Map<String, String>) application.getAttribute("pMap");
        String stage = tran.getStage();
        String possibility = pMap.get(stage);
        tran.setPossibility(possibility);
        mav.addObject("tran", tran);
        mav.setViewName("transactionDetail");
        return mav;
    }


    @RequestMapping(value = "/ssm/transaction/getTranHistoryList", method = RequestMethod.GET)
    @ResponseBody
    public String getTranHistoryListByTranId(@RequestParam("tranId") String tranId,
                                           HttpServletRequest request){
        List<TranHistory> tranHistoryList = tranService.getTranHistory(tranId);
        ServletContext application = request.getServletContext();
        Map<String, String> pMap = (Map<String, String>) application.getAttribute("pMap");
        for (TranHistory tranHistory : tranHistoryList){
            String stage = tranHistory.getStage();
            String value = pMap.get(stage);
            tranHistory.setPossibility(value);
        }

        String jsonByList = JsonArray.ToJsonByList(tranHistoryList);
        return jsonByList;
    }

    @RequestMapping("/ssm/transaction/changeStage")
    public void changeStage(Tran tran,
                            HttpServletRequest request,
                            HttpServletResponse response){
        tran.setEditBy("老高");
        tran.setEditTime(DateTimeUtil.getSysTime());
        Map<String, String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(tran.getStage());
        tran.setPossibility(possibility);
        boolean result = tranService.updateTran(tran);
        Map<String, Object> map = new HashMap<>();
        map.put("tran", tran);
        map.put("success", result);
        PrintJson.printJsonObj(response,map);

    }

    @RequestMapping("/ssm/transaction/tranImage")
    public void setCharts(HttpServletResponse response){
        Map<String, Object> map = tranService.getCharts();
        PrintJson.printJsonObj(response, map);
    }
}
