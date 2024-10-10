package com.metroflow.controller;

import com.metroflow.model.dao.UserDAO;
import com.metroflow.model.dto.User;
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

    @GetMapping("/myPage")
    public String showMyPage(Model model) {
        User currentUser = USERSERVICE.getUserObject();
        model.addAttribute("user", currentUser);
        return "user/myPage";
    }

    @PostMapping("/user/update")
    public String updateUserProfile(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email,
            @RequestParam(value = "profilePic", required = false) String profilePic,
            Model model) {

        User user = USERSERVICE.getUserObject();
        System.out.println("controller 도착");

        try {
            // 사용자 정보 업데이트
            USERSERVICE.updateUser(user, currentPassword, newPassword, confirmPassword, nickname, email, profilePic);
            model.addAttribute("success", "프로필이 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("exception in controller");
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "user/myPage";   // 에러 발생 시 마이페이지로 다시 리다이렉트
        }
        return "redirect:/myPage";  // 성공적으로 업데이트 후 마이페이지로 리다이렉트


    }

}