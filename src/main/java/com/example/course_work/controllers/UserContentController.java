package com.example.course_work.controllers;

import com.example.course_work.entity.Category;
import com.example.course_work.entity.Role;
import com.example.course_work.entity.User;
import com.example.course_work.service.ProductService;
import com.example.course_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserContentController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("sushi",productService.findByCategory(Category.SUSHI));
        model.addAttribute("garnish",productService.findByCategory(Category.GARNISH));
        model.addAttribute("sauce",productService.findByCategory(Category.SAUCE));
        model.addAttribute("drink",productService.findByCategory(Category.DRINK));
        return "user_content/home_page";
    }
    @GetMapping("/account")
    public String editAccount(Model model,@AuthenticationPrincipal UserDetails loggedUser){
        User user=userService.getByEmail(loggedUser.getUsername());
        List<Role> listRoles=userService.listRoles();
        model.addAttribute("user",user);
        model.addAttribute("listRoles",listRoles);
        return "user_content/change_account_info";
    }
}
