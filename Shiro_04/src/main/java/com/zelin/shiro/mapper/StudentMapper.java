package com.zelin.shiro.mapper;


import com.zelin.shiro.pojo.Student;
import com.zelin.shiro.pojo.StudentCustom;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Feng.Wang
 * @Company: Zelin.ShenZhen
 * @Description:
 * @Date: Create in 2019/6/26 10:08
 */
public interface StudentMapper extends Mapper<Student>{
    List<StudentCustom> findAllStudents();
}
