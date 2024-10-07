package com.metroflow.controller;

import com.metroflow.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService USERSERVICE;

    @GetMapping("/")
    public String goHome(Model model/*, HttpSession session*/) {
//        session.invalidate();
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "home";
    }
}
