package com.zelin.shiro.interceptor;

import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.utils.CommUtils;
import com.zelin.shiro.utils.ResourcesUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Component
public class AuthticateInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        String path = CommUtils.getPath(requestURI);

        //1.在匿名用户的路径中
        List<String> anonymousURL = ResourcesUtil.gekeyList("anonymousURL");
        if(anonymousURL.contains(path)){
            return true;
        }

        //2.公共用户的路径
        List<String> commonURL = ResourcesUtil.gekeyList("commonURL");
        if(commonURL.contains(path)){
            return true;
        }
        //3.当前用户是否有访问权限
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if(user!=null){
            List<SysPermission> permissions = user.getPermissions();
            System.out.println("permissions = " + permissions);
            for (SysPermission permission : permissions) {
                String url = permission.getUrl();
                if(url.contains(path)){
                    return true;
                }
            }
        }

        //以上不满足
        response.sendRedirect(request.getContextPath() +"/refuse.html");
        return false;
    }
}
