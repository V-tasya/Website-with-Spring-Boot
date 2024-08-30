package com.example.demo.controllers;

import  org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//class is responsible for all transitions on the site
@Controller
public class MainController {

    //every function is processing url address
    @GetMapping("/") // so, here in the () we are putting what we are processing, "the name of the site / greetings"
    // if the put "/" it is for processing something on the main page
    public String greeting(Model model) {
        model.addAttribute("title", "Main page");
        return "home"; // when we will go for a main page the template home will be opened
    }

    @GetMapping("aboutMe")
    public String aboutMe(Model model) {
        return "aboutMe";
    }
}
