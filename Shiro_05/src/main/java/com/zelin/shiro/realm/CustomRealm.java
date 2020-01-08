package com.zelin.shiro.realm;

import com.zelin.shiro.pojo.SysPermission;
import com.zelin.shiro.pojo.SysUser;
import com.zelin.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取usercode
        String usercode = (String) authenticationToken.getPrincipal();
        SysUser user = userService.findUserByUserCode(usercode);

        //判断
        if(null != user){
            List<SysPermission> menus = userService.findMenusByUsercode(usercode);
            user.setMenus(menus);
            String password = user.getPassword();
            ByteSource saltByte = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,password,saltByte,"myRealm");
            return authenticationInfo;
        }
        return null;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前对象
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //获取当前对象的权限
        List<SysPermission> permissions = userService.findPermissionByUserCode(user.getUsercode());

        //获取全部权限
        List<String> permissionList = new ArrayList<>();
        for (SysPermission permission : permissions) {
            permissionList.add(permission.getPercode());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //添加权限到authorzationInfo
        authorizationInfo.addStringPermissions(permissionList);
        return authorizationInfo;
    }
}
