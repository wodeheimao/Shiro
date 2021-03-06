package com.zelin.shiro.service;

import com.zelin.shiro.pojo.SysPermission;

import java.util.List;

public interface PermssionService {
    List<SysPermission> findAll();


    List<String> findPermissionIdsByRoleId(String roleId);

    void updatePermission(String roleId, String[] permissionIds);
}
