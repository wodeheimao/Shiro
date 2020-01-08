package com.zelin.shiro.controller;

import com.zelin.shiro.pojo.Result;
import com.zelin.shiro.pojo.SysRole;
import com.zelin.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public List<SysRole> findAllRoles(){
        return roleService.findAllRoles();
    }

    //根据用户id查询角色id集合
    @GetMapping("listByUserId")
    public List<String> findRolesIDByUserIds(String userId){
        return roleService.findRolesIDByUserIds(userId);
    }

    //更新用户的角色信息
    @GetMapping("updateRole")
    public Result updateRole(String userId,String[] roleIds){
        try {
            roleService.updateRole(userId,roleIds);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
}
