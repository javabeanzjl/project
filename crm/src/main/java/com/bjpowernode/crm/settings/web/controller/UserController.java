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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                        HttpSession session,
                        HttpServletResponse response) {
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
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("IP受限");
            } else {
                // 登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                // 把user存储到session中，后续操作都需有这个user存在才可进行
                session.setAttribute(Contants.SESSION_USER,user);

                // 如果用户点击10天免登录，则需要向外写cookie
                if ("true".equals(isRemPwd)) {
                    // 创建cookie
                    Cookie cookie = new Cookie("loginAct", user.getLoginAct());
                    // 设置10天限制
                    cookie.setMaxAge(10*24*60*60);
                    response.addCookie(cookie);
                    
                    Cookie cookie1 = new Cookie("loginPwd", user.getLoginPwd());
                    cookie1.setMaxAge(10*24*60*60);
                    response.addCookie(cookie1);
                } else {
                    // 没有选择10天免登录，把没有过期的cookie删除
                    Cookie cookie = new Cookie("loginAct","1");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    Cookie cookie1 = new Cookie("loginPwd","1");
                    cookie1.setMaxAge(0);
                    // 将cookie覆盖
                    response.addCookie(cookie1);
                }
            }
        }

        return returnObject;
    }

    // 安全退出
    @RequestMapping("/settings/qx/user/tologout.do")
    public String loginOut(HttpServletResponse response, HttpSession session) {
        // 清除cookie
        Cookie cookie = new Cookie("loginAct","1");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie cookie1 = new Cookie("loginPwd","1");
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);
        
        // 销毁session
        session.invalidate();
        
        // 跳转到首页,需要重定向才行
        return "redirect:/";
    }
}
