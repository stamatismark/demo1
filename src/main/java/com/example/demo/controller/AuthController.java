package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.model.Professor;
import com.example.demo.model.Student;
import com.example.demo.service.UserService;
import com.example.demo.service.ProfessorService;
import com.example.demo.service.StudentService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    ProfessorService professorService;
    
    @Autowired
    StudentService studentService;
    
    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
       
        if(userService.isUserPresent(user)){
        	
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }

        userService.saveUser(user);
        if(user.getRole().getValue().equals("PROFESSOR")) {
        	Professor professor = new Professor();
        	professor.setHelpUserId(user.getId());
        	professor.setUser(user);
        	professorService.saveProfile(professor);
        }
        else {
	        Student student = new Student();
	        student.setUser(user);
	        studentService.saveStudent(student);
        	
        }
        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/signin";
    }
}