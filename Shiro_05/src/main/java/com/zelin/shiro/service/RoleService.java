package com.zelin.shiro.service;

import com.zelin.shiro.pojo.SysRole;

import java.util.List;

public interface RoleService {
    //查询所有用户
    List<SysRole> findAllRoles();

    //根据用户id查询角色id
    List<String> findRolesIDByUserIds(String userId);

    //更新用户的角色信息
    void updateRole(String userId, String[] roleIds);
}
