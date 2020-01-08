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
import java.util.Collection;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        List<SysPermission> permissions = userService.findPermissionByUserCode(user.getUsercode());
        user.setPermissions(permissions);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<String> permissionsStr = new ArrayList<>();
        for (SysPermission permission : permissions) {
            permissionsStr.add(permission.getPercode());
        }
        authorizationInfo.addStringPermissions(permissionsStr);
        return authorizationInfo;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userCode = (String) authenticationToken.getPrincipal();
        SysUser user = userService.findUserByUserCode(userCode);
        if(user == null){
            return null;
        }
        List<SysPermission> menus = userService.findMenusByUsercode(userCode);
        user.setMenus(menus);
        String password = user.getPassword();
        String salt = user.getSalt();
        System.out.println("password = " + password);
        System.out.println("salt = " + salt);
        ByteSource saltBytes = ByteSource.Util.bytes(salt);
        return new SimpleAuthenticationInfo(user,password,saltBytes,"myRealm");
    }
}
