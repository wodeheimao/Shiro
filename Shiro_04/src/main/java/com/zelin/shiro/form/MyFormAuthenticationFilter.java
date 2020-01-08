package com.zelin.shiro.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String validateCode = (String) session.getAttribute("validateCode");
        String code = req.getParameter("code");
        if(StringUtils.isNotBlank(validateCode) &&!validateCode.equals(code)){
//            req.setAttribute("shiroLoginFailure","validCodeError");
            return true;
        }
        return super.onAccessDenied(request, response);
    }
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request,response,getSuccessUrl(),null,true);
    }
}
