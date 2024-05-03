package com.elice.boardproject.controller;

import com.elice.boardproject.entity.User;
import com.elice.boardproject.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRawPassword(user.getPassword()); // 평문 비밀번호 설정
        userService.createUser(user);
        return "redirect:/boards";

    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }
}