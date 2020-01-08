package com.zelin.shiro.controller;

import com.zelin.shiro.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("login")
    public String login(){
        String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("shiroLoginFailure = " + shiroLoginFailure);
        if(StringUtils.isNotBlank(shiroLoginFailure)){
            if(shiroLoginFailure.equals(UnknownAccountException.class.getName())){
                throw new MyException("未知账户");
            }else if(shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())){
                throw new MyException("用户名或密码有误");
            }else if(shiroLoginFailure.equals(UnauthorizedException.class.getName())){
                throw new MyException("没有访问权限");
            }else if(shiroLoginFailure.equals("validCodeError")){
                throw new MyException("验证码有误");
            }else{
                throw new MyException("其他错误");
            }
        }
        return "login";
    }
}
