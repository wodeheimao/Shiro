package com.zelin.shiro.controller;
import	java.awt.Desktop.Action;

import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    @RequestMapping("menus")
    public String listmenu(Model model){
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        model.addAttribute("user",user);
        return "user/listmenu";
    }

    @RequiresPermissions("user:userlistxx")
    @RequestMapping("userlist")
    public String userList(){
        return "user.userlistxx";
    }

    @GetMapping("list")
    public List<SysUser> findAll(){
        SysUser user = (SysUser) session.getAttribute("user");
        List<SysUser> sysUsers = userService.findAll();
        sysUsers.remove(user);
        return sysUsers;
    }
}
