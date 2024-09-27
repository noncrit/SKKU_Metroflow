package com.metroflow.model.service;

import com.metroflow.model.dto.User;
import com.metroflow.model.dto.UserRegisterForm;
import com.metroflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository USERREPOSITORY;
    private final UserRepository userRepository;

    // ID 중복 체크
    public void idDuplicationCheck(UserRegisterForm user, BindingResult result) {
        if (USERREPOSITORY.findByUserId(user.getUserId()).isPresent()) {
            result.rejectValue("userId", "duplication", "id 중복");
        }
    }

    // 비밀번호 확인
    public void passwordCheck(UserRegisterForm user, BindingResult result) {
        if (!user.getPassword().equals(user.getPasswordCheck())) {
            result.rejectValue("passwordCheck", "notequal", "비밀번호가 다릅니다.");
        }
    }

    // 닉네임 중복 확인
    public void nicknameDuplicationCheck(UserRegisterForm user, BindingResult result) {
        if (USERREPOSITORY.findByNickname(user.getNickname()).isPresent()) {
            result.rejectValue("nickname", "duplication", "닉네임 중복");
        }
    }

    // 현재 세션의 유저 객체 model에 저장하는 메소드
    public User getUserObject() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (auth != null) {
            String userId = auth.getName(); // 로그인한 사용자 id
            user = USERREPOSITORY.findByUserId(userId).get();
        }
        return user;
    }



}