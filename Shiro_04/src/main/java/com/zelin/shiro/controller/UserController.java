package com.zelin.shiro.controller;

import com.zelin.shiro.pojo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by WF on 2019/8/13 15:50
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //1.显示菜单列表
    @RequestMapping("/listmenu")
    public String listmenu(Model model){
        //1.1)得到主体对象
        Subject subject = SecurityUtils.getSubject();
        //1.2)得到登录用户对象
        SysUser user = (SysUser) subject.getPrincipal();
        //1.3)将对象放到model中
        model.addAttribute("user",user);
        return "user/listmenu";
    }
    //下面的注解代表有user:userlistxx这个权限才能访问下面的/user/userlist.do这个资源(授权的第二种方式)
//    @RequiresPermissions("user:userlistxxx")
    @RequestMapping("/userlist")
    public String userxx(){
        return  "user/userlistxx";
    }
}
