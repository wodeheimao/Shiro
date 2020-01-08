package com.zelin.shiro.controller;

import com.zelin.shiro.pojo.Classes;
import com.zelin.shiro.pojo.Student;
import com.zelin.shiro.pojo.StudentCustom;
import com.zelin.shiro.service.ClassesService;
import com.zelin.shiro.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Author: Feng.Wang
 * @Company: Zelin.ShenZhen
 * @Description:
 * @Date: Create in 2019/5/22 14:39
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassesService classesService;
    //查询所有学生
    @RequestMapping("/list")
    public ModelAndView findAll(){
        //1.查询所有学生集合
        List<StudentCustom> studentList = studentService.findAllStudents();
        System.out.println("----" + studentList);
        //2.返回结果ModelAndView
        return new ModelAndView("student/list","students",studentList);
    }
    @RequestMapping("/listaaa")
    public ModelAndView listaaa(){
        return null;
    }
    //到添加学生页面
    @RequestMapping("/toadd")
    public String toadd(Model model){
        //1.查询出所有的班级列表
        List<Classes> classes = classesService.findAll();
        //2.将查询出的数据放到model中
        model.addAttribute("classes",classes);
        return "student/add";   //返回是的逻辑视图名
    }
    //添加学生
    @RequestMapping("/add")
    public String add(Student student){
        studentService.add(student);
        return "redirect:/student/list";
    }
    //到修改页面
    @RequestMapping("/toupdate/{sid}")
    //@PathVariable :代表将请求中的${sid}值取出放到方法的参数sid中，如果两者名字不一样，则可以使用@PathVariable指定取参数的名字
    public String toupdate(@PathVariable Integer sid, Map map){
       //1.查询单个学生信息
       Student student = studentService.findStudentById(sid);
       //2.将学生放到map中
        map.put("student",student);
        map.put("classes",classesService.findAll());
        //3.到修改页面
        return "student/update";
    }
    //修改学生
    @RequestMapping("/update")
    public String update(Student student) {
        //1.修改学生
        studentService.update(student);
        //2.重定向到列表页面
        return "redirect:/student/list";
    }
    //删除学生
    @RequestMapping("/delete/{sid}")
    public String delete(@PathVariable Integer sid){
        studentService.delete(sid);
        return "forward:/student/list";  //转发
    }
}
