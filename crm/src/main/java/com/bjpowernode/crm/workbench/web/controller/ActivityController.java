package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.common.contants.Contants;
import com.bjpowernode.crm.settings.common.utils.UUIDUtils;
import com.bjpowernode.crm.settings.pojo.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.pojo.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ActivityService activityService;
    
    @RequestMapping("/workbench/activity/toIndex.do")
    public String index(HttpServletRequest request) {
        // 查询所有市场活动信息
        List<User> users = userService.queryAllUsers();

        // 将用户信息存储到request域中。
        request.setAttribute("userList",users);
        return "workbench/activity/index";
    }
    
    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name, String owner, String startDate, 
                                                  String endDate, int pageNo, int pageSize,
                                                  HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        // 当页起始数据的位数
        map.put("beginNo",(pageNo - 1) * pageSize);
        // 取几条数据
        map.put("pageSize", pageSize);
        
        // 调用service层，查询数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        // 根据查询结果，生成响应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }
}
