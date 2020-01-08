package com.zelin.shiro.service;



import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;

import java.util.List;

public interface UserService {
    //1.根据用户名及密码得到用户
    SysUser findUserByUsernameAndPassword(String username, String password);
    //2.根据当前登录名得到菜单列表
    List<SysPermission> findMenusByUsercode(String usercode);
    //3.通过用户名得到用户对象
    SysUser findUserByUserCode(String userCode);

    List<SysPermission> findPermissionByUserCode(String usercode);

    List<SysUser> findAll();
}
