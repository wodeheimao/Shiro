package com.zelin.shiro.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(MyException.class)
    public String error(MyException ex,Model model) {
        String message = "未知异常！";
        if(ex!=null){
            message = ex.getMessage();
        }
        model.addAttribute("message",message);
        System.out.println("message = " + message);
        return "login";
    }
}
