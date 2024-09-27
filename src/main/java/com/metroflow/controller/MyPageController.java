package com.metroflow.controller;

import ch.qos.logback.core.model.Model;

import com.metroflow.model.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

public class MyPageController {

    private final UserService userService;

    public MyPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String myPage(Model model, Authentication authentication) {
        UserDetails userDetails =(UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();


        return "myPage";
    }
}
