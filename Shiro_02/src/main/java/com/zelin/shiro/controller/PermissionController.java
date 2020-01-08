package com.zelin.shiro.controller;

import com.zelin.shiro.pojo.Result;
import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.service.PermssionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermssionService permssionService;

    @GetMapping("list")
    public List<SysPermission> findAllPermission(){
        return permssionService.findAll();
    }

    @GetMapping("listByRoleId")
    public List<String> findPermissionIdsByRoleId(String roleId){
        return permssionService.findPermissionIdsByRoleId(roleId);
    }


    //更新用户的角色权限信息
    @GetMapping("updatePerission")
    public Result updatePermission(String roleId, String[] permissionIds){
        try {
            permssionService.updatePermission(roleId,permissionIds);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
}
