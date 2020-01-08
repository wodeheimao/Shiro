package com.zelin.shiro.controller;

import com.zelin.shiro.pojo.Result;
import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired

    private HttpServletRequest request;

    @RequestMapping("login")
    public Result login(String username, String password){

        SysUser user = userService.findUser(username, password);
        if(user!=null){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return new Result(true,"登录成功");
        }
        return new Result(false,"登录失败");
    }
}
