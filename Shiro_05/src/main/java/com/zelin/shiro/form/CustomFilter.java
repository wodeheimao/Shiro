package com.zelin.shiro.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//自定义filter过滤器
public class CustomFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //获取shiroLoginFailure
        String shiroLoginFailure = (String) req.getAttribute("shiroLoginFailure");
        System.out.println("shiroLoginFailure = " + shiroLoginFailure);
        //获取验证码
        String validatecode = (String) req.getSession().getAttribute("validatecode");
        String code = (String) req.getAttribute("code");
        if(StringUtils.isNotBlank(validatecode) && !validatecode.equals(code)){
            req.setAttribute("shiroLoginFailure","validCodeError");
            return true;
        }
        return super.onAccessDenied(request, response);
    }
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request,response,getSuccessUrl(),null,true);
    }
}
