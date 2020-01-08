package com.zelin.shiro.interceptor;

import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.utils.CommUtils;
import com.zelin.shiro.utils.ResourcesUtil;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//认证拦截器
@Component
public class AuthrizatorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        String path = CommUtils.getPath(requestURI);

        //1.是否是匿名用户中的允许访问的路径
        List<String> anonymousURL = ResourcesUtil.gekeyList("anonymousURL");
        if(anonymousURL.contains(path)){
            return true;
        }

        //2.用户是否存在于session中
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if(user!=null){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login.html");
        return false;
    }
}
