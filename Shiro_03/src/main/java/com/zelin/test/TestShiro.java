package com.zelin.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestShiro {

    private Subject getSubject(String path) {
        //获取securityManagerFactory
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory(path);
        //2.得到securityMananger
        SecurityManager securityManager = securityManagerFactory.getInstance();
        //将securityManager设置到上下文中
        SecurityUtils.setSecurityManager(securityManager);

        return SecurityUtils.getSubject();
    }

    @Test
    public void test01(){
        Subject subject = getSubject("classpath:first_shiro.ini");

        AuthenticationToken token = new UsernamePasswordToken("lisi","111111");
        //认证
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
    }
    @Test
    public void test02(){
        Subject subject = getSubject("classpath:realm.ini");

        AuthenticationToken token = new UsernamePasswordToken("lisi","111111");
        //认证
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
    }

    @Test
    public void test03(){
        //使用HashedCredentialsMatcher来对密码加密
        Subject subject = getSubject("classpath:saltRealm.ini");
        AuthenticationToken token = new UsernamePasswordToken("lisi","111111");
        //认证
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
    }

    @Test
    public void test04(){
        //使用Md5CredentialsMatcher来对密码加密
        Subject subject = getSubject("classpath:saltRealm.ini");
        AuthenticationToken token = new UsernamePasswordToken(null,"111111");
        //认证
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
    }

    @Test
    public void test05(){
        //使用Md5CredentialsMatcher来对密码加密
        Subject subject = getSubject("classpath:saltRealm.ini");
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","111111");
        //认证
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
    }
    @Test
    public void test06(){
        //一：认证
        Subject subject = getSubject("classpath:permission_shiro.ini");
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","111111");
        //认证连接
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);

        //二： 授权
        //是否有单个角色
        boolean role1 = subject.hasRole("role1");
        System.out.println("拥有role1 = " + role1);
        //是否含有多个角色
        List<String> roles = Arrays.asList("role1","role2");
        boolean b = subject.hasAllRoles(roles);
        System.out.println("同时拥有role1+role2 = " + b);

        //单个权限判断拥有
        boolean permitted1 = subject.isPermitted("student:delete");
        System.out.println("拥有student:delete = " + permitted1);
        //多个权限判断拥有
        boolean permitted2 = subject.isPermittedAll("student:delete","student:update");
        System.out.println("拥有student:delete = " + permitted1);

        //使用check不含有就会抛异常
        subject.checkRole("role2");
        subject.checkPermission("student:query");

    }


    @Test
    public void test07(){
        //一：认证
        Subject subject = getSubject("classpath:permission_realm.ini");
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","111111");
        //认证连接
        subject.login(token);
        //认证是否成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("authenticated = " + authenticated);

        //二： 授权
        //单个权限
        boolean b = subject.isPermitted("student:create");
        System.out.println("student:create= " + b);
        //多个权限
        boolean permittedAll = subject.isPermittedAll("student:create", "student:update","student:query");
        System.out.println("student:create + tudent:update + student:query = " + permittedAll);
        //检查单个权限
        subject.checkPermission("student:delete");
        //检查多个权限
        subject.checkPermissions("student:create", "student:update","student:query");

    }
}
