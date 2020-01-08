package com.zelin.shiro.config;
import java.util.HashMap;
import	java.util.LinkedHashMap;

import com.zelin.shiro.form.CustomFilter;
import com.zelin.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.Map;
import java.util.Properties;


@Configuration
public class MyConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
        //配置登录页面、登录成功、授权不通过、securityManager管理器
        shiroFilterFactory.setLoginUrl("/login");
        shiroFilterFactory.setSuccessUrl("/user/menus");
        shiroFilterFactory.setUnauthorizedUrl("/refuse.jsp");
        shiroFilterFactory.setSecurityManager(securityManager);

        //配置过滤器映射
        Map<String, String> filterChainMap = new LinkedHashMap<> ();
        filterChainMap.put("/static/**","anon");           //静态文件放行
        filterChainMap.put("/plugins/**","anon");           //静态文件放行
        filterChainMap.put("/validatecode.jsp","anon");     //验证码放行
        filterChainMap.put("/logout","logout");         //退出
        filterChainMap.put("/**","authc");              //需要权限过滤的
        shiroFilterFactory.setFilterChainDefinitionMap(filterChainMap);

        //设置自定义的filter
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("authc",new CustomFilter());
        shiroFilterFactory.setFilters(filterMap);
        return shiroFilterFactory;
    }

    //获取安全管理器，注入到容器,需要注入缓存和realm 记住我
    @Bean
    public SecurityManager getSecurityManager(CustomRealm realm, CacheManager cacheManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入realm域
        securityManager.setRealm(realm);
        //注入缓存
        securityManager.setCacheManager(cacheManager);
        //注入到记住我管理器
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    //获取cacheManager
    @Bean
    public CacheManager getCacheManager() {
        EhCacheManager cacheManager =new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-cache.xml");
        return cacheManager;
    }
    //获取CustomRealm
    @Bean
    public CustomRealm getRealm(CredentialsMatcher credentialsMatcher) {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(credentialsMatcher);
        return customRealm;
    }
    //设置凭证匹配器
    @Bean
    public CredentialsMatcher getCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);
        return credentialsMatcher;
    }

    //spring中的bean， 由adviceor决定对哪些方法进行aop代理
//    <aop:config proxy-target-class="true"/>
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /*配置，开启权限注解
     <!--开启shiro缓存-->
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>*/
    @Bean
    public AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    //通用异常配置
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver(){
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties mapperings = new Properties();
        mapperings.setProperty("UnauthorizedException","refuse");
        mapperings.setProperty("UnauthenticatedException","refuse");
        exceptionResolver.setExceptionMappings(mapperings);
        return exceptionResolver;
    }

    //配置记住我功能
    @Bean
    public RememberMeManager rememberMeManager(Cookie cookie){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    //配置cookie
    @Bean
    public Cookie cookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setMaxAge(3600*24*7);
        cookie.setName("rememberMe");
        return cookie;
    }

}
