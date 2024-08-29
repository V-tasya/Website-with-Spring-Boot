package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    //Autowired for creating variables which refer to the repo
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/Blog")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll(); //findAll() finds and pull out all the records in tab
        model.addAttribute("posts", posts); // in the template we are transfer all the reports, we will transfer them by name post and the value will be an array posts with all of records
        return "Blog";
    }

    @GetMapping("/Blog/add")
    public String blogAdd(Model model){
        return "BlogAdd";
    }
}
