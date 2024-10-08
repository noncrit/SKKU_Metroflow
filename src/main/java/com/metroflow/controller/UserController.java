package com.metroflow.controller;

import com.metroflow.model.dao.UserDAO;
import com.metroflow.model.dto.UserRegisterForm;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor

public class UserController {

    private final UserDAO USERDAO;
    private final UserService USERSERVICE;

    @Value("${profile.upload-dir}")
    private String uploadDir;

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

//    // 비밀 번호 변경
//    @PostMapping("/myPage")
//    public String updatePassword(@RequestParam String userId,
//                                 @RequestParam String email,
//                                 @RequestParam String currentPassword,
//                                 @RequestParam String newPassword,
//                                 @RequestParam String confirmPassword) {
//        if (!newPassword.equals(confirmPassword)) {
//            return "새로은 비밀번호가 일치하지 않습니다.";
//        }
//
//        boolean isUpdated = USERSERVICE.updatePassword(userId, currentPassword, newPassword);
//        if (isUpdated) {
//            USERSERVICE.up
//        }
//
//    }

//    // 프로필 사진 변경
//    @PostMapping("/myPage/profilePic")
//    public ResponseEntity<String> changeProfilePic(
//            @RequestParam String userId,
//            @RequestParam String userImgPath) {
//        USERSERVICE.updateProfileImage(userId, userImgPath);
//        return ResponseEntity.ok("Profile image updated successfully");
//
//    }




}