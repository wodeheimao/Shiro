package com.zelin.shiro.service;



import com.zelin.shiro.pojo.Student;
import com.zelin.shiro.pojo.StudentCustom;

import java.util.List;


public interface StudentService {
    List<Student> findAll();

    void add(Student student);

    Student findStudentById(Integer sid);

    void update(Student student);

    void delete(Integer sid);

    List<StudentCustom> findAllStudents();
}
