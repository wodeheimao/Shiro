package com.zelin.shiro.service;

import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;

import java.util.List;

/**
 * Created by WF on 2019/10/28 15:29
 */
public interface UserService {
    //1.根据用户名查询用户是否存在
    SysUser findUser(String username, String password);
    //2.查询所有用户
    List<SysUser> findAll();
    //3.根据当前usercode找菜单列表
    List<SysPermission> findMenusByUsercode(String usercode);
    //4.根据当前usercode找权限列表
    List<SysPermission> findPermissionsByUsercode(String usercode);
}
