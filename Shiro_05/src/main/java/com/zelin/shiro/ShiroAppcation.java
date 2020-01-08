package com.zelin.shiro;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.zelin.shiro.mapper")
public class ShiroAppcation {
    public static void main(String[] args) {
        SpringApplication.run(ShiroAppcation.class);
    }
}
