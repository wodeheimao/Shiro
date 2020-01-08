package com.zelin.shiro.config;

import com.zelin.shiro.interceptor.AuthrizatorInterceptor;
import com.zelin.shiro.interceptor.AuthticateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthrizatorInterceptor authrizatorInterceptor;

    @Autowired
    private AuthticateInterceptor authticateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //认证拦截器拦截的路径
        registry.addInterceptor(authrizatorInterceptor).addPathPatterns("/users/*","/student/*");

        //用户授权的拦截器
        registry.addInterceptor(authticateInterceptor).addPathPatterns("/users/*","/student/*");

    }
}
