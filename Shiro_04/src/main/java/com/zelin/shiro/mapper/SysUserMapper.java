package com.zelin.shiro.mapper;


import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Feng.Wang
 * @Company: Zelin.ShenZhen
 * @Description:
 * @Date: Create in 2019/6/26 10:27
 */
public interface SysUserMapper extends Mapper<SysUser> {
    /**
     * 根据当前登录用户查询出此用户的所有菜单列表
     * @param usercode
     * @return
     */
    public List<SysPermission> findMenusByUserCode(String usercode);

    /**
     * 根据当前登录用户查询出此用户的所有权限列表
     * @param usercode
     * @return
     */
    public List<SysPermission> findPermissionsByUserCode(String usercode);

}
