package com.zelin.shiro.service;


import com.zelin.shiro.pojo.Student;
import com.zelin.shiro.pojo.StudentCustom;

import java.util.List;

/**
 * @Author: Feng.Wang
 * @Company: Zelin.ShenZhen
 * @Description:
 * @Date: Create in 2019/5/22 14:40
 */
public interface StudentService {
    List<Student> findAll();

    void add(Student student);

    Student findStudentById(Integer sid);

    void update(Student student);

    void delete(Integer sid);

    List<StudentCustom> findAllStudents();
}
