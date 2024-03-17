package com.example.course_work.controllers;

import com.example.course_work.entity.Role;
import com.example.course_work.entity.User;
import com.example.course_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService service;

    @GetMapping("/login")
    public String getLoginPage(Model model){
        List<Role> listRoles=service.listRoles();
        Role role=listRoles.get(1);
        User user=new User();
        user.setEnabled(true);
        model.addAttribute("user",user);
        model.addAttribute("role",role);
        return "authorization/login";
    }

}
