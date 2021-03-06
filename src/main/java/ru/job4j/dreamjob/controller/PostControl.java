package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public final class PostControl {
    private final PostService postService;
    private final CityService cityService;

    public PostControl(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("user", getUserFromSession(session));
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        /*model.addAttribute("post", new Post(0, "Заполните поле"));*/
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("user", getUserFromSession(session));
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, @RequestParam("city.id") int id) {
        post.setCity(cityService.findById(id));
        postService.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("user", getUserFromSession(session));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post, @RequestParam("city.id") int id) {
        post.setCity(cityService.findById(id));
        postService.update(post);
        return "redirect:/posts";
    }

    private User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
