package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.pojo.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {
    
    @Autowired
    private UserService userService;
    
    // 业务主页面
    @RequestMapping("/workbench/main/toindex.do")
    public String toIndex() {
        
        return "workbench/main/index";
    }
}
