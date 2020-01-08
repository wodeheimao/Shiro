package com.zelin.shiro.mapper;


import com.zelin.shiro.pojo.Student;
import com.zelin.shiro.pojo.StudentCustom;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentMapper extends Mapper<Student>{
    List<StudentCustom> findAllStudents();
}
