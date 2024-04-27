package com.elice.boardproject.controller;

import ch.qos.logback.core.model.Model;
import com.elice.boardproject.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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
    }
