package com.metroflow.controller;

import com.metroflow.model.dto.User;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService USERSERVICE;

    @GetMapping("/")
    public String goHome(Model model) {
        model.addAttribute("user", USERSERVICE.getUserObject());
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("user", USERSERVICE.getUserObject());
        return "home";
    }
}
