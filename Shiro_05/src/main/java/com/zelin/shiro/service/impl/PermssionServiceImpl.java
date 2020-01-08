package com.zelin.shiro.service.impl;

import com.zelin.shiro.mapper.SysPermissionMapper;
import com.zelin.shiro.mapper.SysRolePermissionMapper;
import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysRolePermission;
import com.zelin.shiro.service.PermssionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PermssionServiceImpl implements PermssionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    
    //查询所有权限
    @Override
    public List<SysPermission> findAll() {
        return sysPermissionMapper.selectAll();
    }

    @Override
    public List<String> findPermissionIdsByRoleId(String roleId) {
        List<String> permissionIds = new ArrayList<>();
        //查询条件
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setSysRoleId(roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.select(sysRolePermission);
        //得到PermissionId
        for (SysRolePermission rolePermission : sysRolePermissions) {
            permissionIds.add(rolePermission.getSysPermissionId());
        }
        return permissionIds;
    }

    @Override
    public void updatePermission(String roleId, String[] permissionIds) {
        //删除原有的权限信息、
        SysRolePermission rolePermission = new SysRolePermission();
        rolePermission.setSysRoleId(roleId);
        sysRolePermissionMapper.delete(rolePermission);

        //新增现有的角色信息
        for (String permissionId : permissionIds) {
            if(!permissionId.equals("0")){
                rolePermission.setId(UUID.randomUUID().toString());
                rolePermission.setSysPermissionId(permissionId);
                sysRolePermissionMapper.insert(rolePermission);
            }
        }
    }

}
