package com.registration.loginAndRegister.controller;

import com.registration.loginAndRegister.model.User;
import com.registration.loginAndRegister.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository repo;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);

        return "register_success";
    }

    @GetMapping("/list_users")
    public String viewUserList(Model model, HttpServletRequest request) {
        List<User> listUsers = repo.findAll();
        model.addAttribute("request",request);
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

}
