package com.zelin.shiro.controller;

import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    @RequestMapping("listmenu")
    public List<SysPermission> findMenus(){
        SysUser user = (SysUser) session.getAttribute("user");
        if(user!=null){
            return userService.findMenusByUsercode(user.getUsercode());
        }
        return null;
    }

    @GetMapping("list")
    public List<SysUser> findAll(){
        SysUser user = (SysUser) session.getAttribute("user");
        List<SysUser> sysUsers = userService.findAll();
        sysUsers.remove(user);
        return sysUsers;
    }

}
