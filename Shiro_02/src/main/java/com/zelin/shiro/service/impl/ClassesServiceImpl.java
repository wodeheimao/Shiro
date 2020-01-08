package com.zelin.shiro.service.impl;

import com.zelin.shiro.mapper.ClassesMapper;
import com.zelin.shiro.pojo.Classes;
import com.zelin.shiro.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Feng.Wang
 * @Company: Zelin.ShenZhen
 * @Description:
 * @Date: Create in 2019/5/22 14:57
 */
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
