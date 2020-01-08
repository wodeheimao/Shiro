package com.zelin.shiro.service.impl;


import com.zelin.shiro.mapper.SysUserMapper;
import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.service.UserService;
import com.zelin.shiro.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserSerivceImpl implements UserService {
    @Autowired
    private SysUserMapper userMapper;
    //1.根据用户名及密码得到用户
    @Override
    public SysUser findUserByUsernameAndPassword(String username, String password) {
        //1.1)根据用户名得到用户对象
        SysUser sysUser = userMapper.selectByPrimaryKey(username);
        //1.2)如果不为null, 就获取密码
        if(sysUser != null){
            String password1 = sysUser.getPassword();   //数据库中的密码
            //1.3)将我们输入的密码进行加密处理
            MD5 md5 = new MD5();
            password = md5.getMD5ofStr(password);
            //1.4)比较两个密码是否相等
            if(StringUtils.isNotEmpty(password) && password.equals(password1)){
                //1.4.1)得到当前登录用户所关联的菜单对象
                List<SysPermission> menus = userMapper.findMenusByUserCode(username);
                //1.4.2)得到当前登录用户所关联的权限列表
                List<SysPermission> permissions = userMapper.findPermissionsByUserCode(username);
                //1.5)与用户进行绑定
                sysUser.setMenus(menus);
                sysUser.setPermissions(permissions);
                return sysUser;
            }
        }
        return null;
    }

    @Override
    public List<SysPermission> findMenusByUsercode(String usercode) {
        return userMapper.findMenusByUserCode(usercode);
    }

    @Override
    public SysUser findUserByUserCode(String userCode) {
        return userMapper.selectByPrimaryKey(userCode);
    }

    @Override
    public List<SysPermission> findPermissionByUserCode(String usercode) {
        return userMapper.findPermissionsByUserCode(usercode);
    }

    @Override
    public List<SysUser> findAll() {
        return userMapper.selectAll();
    }
}
