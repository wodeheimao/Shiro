package com.zelin.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.util.ByteSource;

//只继承
public class SaltRealm extends AuthenticatingRealm {
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object principal = token.getPrincipal();
        if (principal == null) {
            return null;
        }
        String password = "ec1b86316c81b3f3440c07f65a74bf79";
        ByteSource salt = ByteSource.Util.bytes("rbtwy");
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, password, salt, "myRealm");
        return authenticationInfo;
    }
}
