package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public final class UserControl {
    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", getUserFromSession(session));
        return "users";
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        return "success";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        return "fail";
    }

    @GetMapping("/formUpdateUser/{userId}")
    public String formUpdateUser(Model model, @PathVariable("userId") int id, HttpSession session) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("currentUser", getUserFromSession(session));
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
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
