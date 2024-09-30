package com.metroflow.controller;

import com.metroflow.model.dao.UserDAO;
import com.metroflow.model.dto.UserRegisterForm;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserDAO USERDAO;
    private final UserService USERSERVICE;
    private final UserRepository USERREPOSITORY;

    @GetMapping("/goRegister")
    public String goRegisterPage(Model model) {
        model.addAttribute("user", new UserRegisterForm());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegisterForm user, BindingResult result,
                           @RequestParam("imagePath") String imagePath) {
        USERSERVICE.idDuplicationCheck(user, result);
        USERSERVICE.passwordCheck(user, result);
        USERSERVICE.nicknameDuplicationCheck(user, result);
        if (result.hasErrors()) {
            return "user/register";
        }
        USERDAO.register(user, imagePath);
        return "home";
    }

    @GetMapping("/login")
    public String goLoginPage() {
        return "user/login";
    }

    @GetMapping("/myPage")
    public String showMyPage() {

        return "user/myPage";
    }


}