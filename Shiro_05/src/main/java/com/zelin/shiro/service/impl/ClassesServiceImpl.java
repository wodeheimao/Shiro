package com.zelin.shiro.service.impl;


import com.zelin.shiro.mapper.ClassesMapper;
import com.zelin.shiro.pojo.Classes;
import com.zelin.shiro.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesMapper classesMapper;
    //查询所有的班级
    @Override
    public List<Classes> findAll() {
        return classesMapper.selectAll();
    }
}
