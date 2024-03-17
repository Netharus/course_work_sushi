package com.example.course_work.controllers;

import com.example.course_work.entity.User;
import com.example.course_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @PostMapping("/account/create_new")
    public String createAccount(User user){
        System.out.println(user);
        userService.createNew(user);
        return "redirect:/success";
    }
    @PostMapping("/account/save")
    public String saveAccount(User user){
        System.out.println(user);
        userService.save(user);
        return "authorization/success_update";
    }
    @GetMapping("/success")
    public String success(){
        return "authorization/success_new_account";
    }
}
