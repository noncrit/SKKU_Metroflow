package com.metroflow.controller;

import com.metroflow.model.dao.UserDAO;
import com.metroflow.model.dto.UserRegisterForm;
import com.metroflow.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    // 회원가입으로 가는 메소드
    @GetMapping("/goRegister")
    public String goRegisterPage(Model model) {
        model.addAttribute("user", new UserRegisterForm());
        return "user/register";
    }

    // 회원가입 메소드
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegisterForm user, BindingResult result,
                           @RequestParam("imagePath") String imagePath) {
        USERSERVICE.idDuplicationCheck(user, result); // id 중복 체크
        USERSERVICE.passwordCheck(user, result); // 비밀번호 체크(비밀번호 확인과 값이 같은지)
        USERSERVICE.nicknameDuplicationCheck(user, result); // 닉네임 중복 체크
        if (result.hasErrors()) {
            return "user/register";
        }
        USERDAO.register(user, imagePath);
        return "home";
    }

    // 로그인 화면으로 가는 메소드
    @GetMapping("/goLogin")
    public String goLoginPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", ""); // 에러 메세지 초기화
        return "user/login";
    }

    // 로그인 메소드
    @GetMapping("/login")
    public String login() {
        return"user/login";
    }


}