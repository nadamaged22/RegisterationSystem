package com.example.RegisterationSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ContentController {
    @GetMapping("/login")
    public String login(){
        return "login"; //return the html file responsible for the login page
    }

    @GetMapping("/signup")
    public String Signup(){
        return "Signup";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
