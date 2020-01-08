package com.zelin.shiro.service.impl;

import com.zelin.shiro.mapper.ClassesMapper;
import com.zelin.shiro.mapper.StudentMapper;
import com.zelin.shiro.pojo.Student;
import com.zelin.shiro.pojo.StudentCustom;
import com.zelin.shiro.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Feng.Wang
 * @Company: Zelin.ShenZhen
 * @Description:
 * @Date: Create in 2019/5/22 14:42
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassesMapper classesMapper;

    //查询所有学生
    @Override
    public List<StudentCustom> findAllStudents() {
        //1.得到学生列表
        List<StudentCustom> students = studentMapper.findAllStudents();

        return students;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    //添加学生
    @Override
    public void add(Student student) {
        studentMapper.insert(student);
    }
    //到修改页面
    @Override
    public Student findStudentById(Integer sid) {
        return studentMapper.selectByPrimaryKey(sid);
    }
    //修改学生
    @Override
    public void update(Student student) {
        studentMapper.updateByPrimaryKey(student);
    }
    //删除学生
    @Override
    public void delete(Integer sid) {
        studentMapper.deleteByPrimaryKey(sid);
    }
}
