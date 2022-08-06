package com.bjpowernode.crm.settings.web.interceptor;

import com.bjpowernode.crm.settings.common.contants.Contants;
import com.bjpowernode.crm.settings.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果用户没有进行登录就进行业务页面的访问，则拦截请求并跳转回登录界面
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Contants.SESSION_USER);

        if (user == null) {
            // user为空，说明没登陆，跳转回登录界面，使用重定向
            response.sendRedirect(request.getContextPath());
            return false;
        }
        return true;
    }
}
