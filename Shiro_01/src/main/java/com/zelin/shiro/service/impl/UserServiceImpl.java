package com.zelin.shiro.service.impl;

import com.zelin.shiro.mapper.SysUserMapper;
import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.pojo.SysUserExample;
import com.zelin.shiro.service.UserService;
import com.zelin.shiro.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WF on 2019/10/28 15:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper userMapper;
    //1.根据用户名查询用户是否存在
    @Override
    public SysUser findUser(String username, String password) {
        //1.1)根据用户名查询用户
        //1.1.1)定义查询实例
        SysUserExample example = new SysUserExample();
        //1.1.2)定义查询实例关联的查询条件
        SysUserExample.Criteria criteria = example.createCriteria();
        //1.1.3）添加查询条件
        criteria.andUsernameEqualTo(username);
        //1.2)根据用户名查询用户对象
        SysUser user = userMapper.selectOneByExample(example);

        //1.3)将输入输入的密码进行md5加密
        MD5 md5 = new MD5();
        String md5ofStr = md5.getMD5ofStr(password);
        //1.4)将加密后的密码与数据库得到的密码进行比较
        if(user != null && md5ofStr.equals(user.getPassword())){
            //1.5）查询用户菜单列表
            List<SysPermission> menus = findMenusByUsercode(user.getUsercode());
            //1.6)将菜单与用户绑定
            user.setMenus(menus);
            //1.7)查询用户权限列表
            List<SysPermission> permissions = findPermissionsByUsercode(user.getUsercode());
            //1.8)将user与权限列表进行绑定
            user.setPermissions(permissions);
            return user;
        }
        return null;
    }

    @Override
    public List<SysUser> findAll() {
        return userMapper.selectAll();
    }

    //2.根据用户id查询菜单列表
    public List<SysPermission> findMenusByUsercode(String usercode) {
        return userMapper.findMenusByUserCode(usercode);
    }
    //3.根据用户id查询权限列表
    public List<SysPermission> findPermissionsByUsercode(String usercode) {
        return userMapper.findPermissionsByUserCode(usercode);
    }

}
