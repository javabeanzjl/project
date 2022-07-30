package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.common.contants.Contants;
import com.bjpowernode.crm.settings.common.pojo.ReturnObject;
import com.bjpowernode.crm.settings.common.utils.DateUtils;
import com.bjpowernode.crm.settings.pojo.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    // 跳转到登录界面
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String index() {

        return "settings/qx/user/login";
    }

    /**
     * 进行登录验证
     * @return
     */
    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd,
                        HttpServletRequest request,
                        HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        
        // 查询用户
        User user = (User) userService.queryUser(map);
        
        // 生成响应信息
        ReturnObject returnObject = new ReturnObject();
        
        // 进一步判断账户信息
        if (user == null) {
            // 登录失败，提示用户名或密码输入错误
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码输入错误！");
        } else { // 进一步判断账户是否合法
            String nowStr = DateUtils.formateDateTime(new Date());
            if (nowStr.compareTo(user.getExpireTime()) > 0) {
                // 账户过期了
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账户已过期!");
            } else if ("0".equals(user.getLockState())) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账户已锁定!");
            } else if (user.getAllowIps().contains(request.getRemoteAddr())) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("IP受限");
            } else {
                // 登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                // 把user存储到session中，后续操作都需有这个user存在才可进行
                session.setAttribute(Contants.SESSION_USER,user);
            }
        }
        
        return returnObject;
    }
}
