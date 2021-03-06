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

/**
 * Created by WF on 2020/1/4 9:47
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private HttpServletRequest request;

    //如果有错误发生，经过表单过滤器，会返回一个key为shiroLoginFailure，value为异常的名字
    @RequestMapping("login")
    public String login(){

        String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("shiroLoginFailure = " + shiroLoginFailure);
        if(StringUtils.isNotBlank(shiroLoginFailure)){
            if(shiroLoginFailure.equals(UnknownAccountException.class.getName())) {
                throw new MyException("出现未知账户异常错误");
            }else if(shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())){
                throw new MyException("密码不正确");
            }else if(shiroLoginFailure.equals(UnauthorizedException.class.getName())){
                throw new MyException("对不起没有访问权限");
            }else if(shiroLoginFailure.equals("validCodeError")){
                throw new MyException("出现验证码错误");
            }else {
                throw new MyException("其他错误");
            }
        }
        return "login";
    }


}
