package com.zelin.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PermissionRealm extends AuthorizingRealm {

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = new ArrayList<String>();
        permissions.add("student:create");
        permissions.add("student:update");
        permissions.add("student:delete");
        permissions.add("student:query");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object principal = token.getPrincipal();
        if(principal == null) return null;
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal,"111111","myRealm");
        return authenticationInfo;
    }
}
