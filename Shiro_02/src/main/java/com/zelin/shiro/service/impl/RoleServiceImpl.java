package com.zelin.shiro.service.impl;

import com.zelin.shiro.mapper.SysRoleMapper;
import com.zelin.shiro.mapper.SysUserRoleMapper;
import com.zelin.shiro.pojo.SysRole;
import com.zelin.shiro.pojo.SysUserRole;
import com.zelin.shiro.pojo.SysUserRoleExample;
import com.zelin.shiro.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> findAllRoles() {
        return sysRoleMapper.selectAll();
    }

    @Override
    public List<String> findRolesIDByUserIds(String userId) {
        List<String> roleIds= new ArrayList<String>();
        SysUserRole userRole = new SysUserRole();
        userRole.setSysUserId(userId);
        List<SysUserRole> userRoles = sysUserRoleMapper.select(userRole);
        for (SysUserRole role : userRoles) {
            System.out.println("role.getSysRoleId() = " + role.getSysRoleId());
            roleIds.add(role.getSysRoleId());
        }
        return roleIds;
    }

    //更新用户的角色信息
    @Override
    public void updateRole(String userId, String[] roleIds) {
        //删除原有的角色信息
        SysUserRole userRole = new SysUserRole();
        userRole.setSysUserId(userId);
        sysUserRoleMapper.delete(userRole);

        //新增现有的角色信息
        for (String roleId : roleIds) {
            if(!roleId.equals("0")){
                SysUserRole newuUserRole = new SysUserRole();
                newuUserRole.setId(UUID.randomUUID().toString());
                newuUserRole.setSysUserId(userId);
                newuUserRole.setSysRoleId(roleId);
                sysUserRoleMapper.insert(newuUserRole);
            }
        }
    }

}