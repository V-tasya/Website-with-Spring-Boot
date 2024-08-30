package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    //Autowired for creating variables which refer to the repo
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/Blog")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll(); //findAll() finds and pull out all the records in tab
        model.addAttribute("posts", posts); // we are transfer all the reports in the template, we will transfer them by name post and the value will be an array posts with all of records
        return "Blog";
    }

    @GetMapping("/BlogAdd")
    public String blogAdd(Model model){
        return "BlogAdd";
    }

    //To get the data from the form
   @PostMapping("/BlogAdd")
    public String getData(@RequestParam String title,
                          @RequestParam String anons,
                          @RequestParam String fullText,
                          Model model) {
        // @RequestParam String title to get the data from the first field by name title
       Post post = new Post(title, anons, fullText);
       postRepository.save(post); // added the object into db
       return "redirect:/Blog";
   }

   /* @GetMapping("/Blog/{id}") // here we are searching for dynamic url
    public String articleDetails(@PathVariable(value = "id") long id, Model model){
        /* @PathVariable(value = "id") to take one of the dynamic variables in the url
         long id created this variable, long because the type of id in Post is long
        Optional<Post> post = postRepository.findById(id); // records that we receive from the function 'postRepository.findById(id)' we need to put into object by class Optional
        /* findById() is searching for the object in db
        and the result of the search can be an object that was found or empty optional if the object by id wasn't found, in this case Optional<Post> will be empty
        the result of the search assigned to the variable post
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add); /*performs the specified actions if the object is presents
        result::add - added post into an array
        model.addAttribute("post", result);
        return "BlogDetails";
    } */

    @GetMapping("/blog/{id}")
    public String articleDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/Blog";
        }
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/Blog";
        }
        model.addAttribute("post", post.get());
        return "BlogDetails";
    }

    @GetMapping("/blog/{id}/Edit")
    public String edit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "redirect:/Blog";
        }
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/Blog";
        }
        model.addAttribute("post", post.get());
        return "Edit";
    }

    @PostMapping("/blog/{id}/Edit")
    public String updatePost(@PathVariable(value = "id") long id,
                         @RequestParam String title,
                          @RequestParam String anons,
                          @RequestParam String fullText,
                          Model model) {
        Post post = postRepository.findById(id).orElseThrow(); //orElseThrow() throwing exception if the article wasn't found
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postRepository.save(post);
        return "redirect:/Blog";
    }

    @PostMapping("/blog/{id}/Delete")
    public String deletePost(@PathVariable(value = "id") long id,
                         Model model) {
        Post post = postRepository.findById(id).orElseThrow(); //orElseThrow() throwing exception if the article wasn't found
        postRepository.delete(post);
        return "redirect:/Blog";
    }
}
