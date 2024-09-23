package com.metroflow.controller;

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

    private final UserRepository USERREPOSITORY;

    @GetMapping("/")
    public String goHome(Model model, User user) {
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        // SecurityContext에서 인증 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String userId = auth.getName(); // 로그인한 사용자 이름
            model.addAttribute("user", USERREPOSITORY.findByUserId(userId).get());
        }
        return "home";
    }
}
