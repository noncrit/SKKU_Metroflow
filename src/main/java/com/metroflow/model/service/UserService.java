package com.metroflow.model.service;

import com.metroflow.model.dto.*;
import com.metroflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository USERREPOSITORY;

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
        User user = null;

        System.out.println("auth : " + auth);
        if (auth != null) {
            String userId = auth.getName(); // 로그인한 사용자 id
            Optional<User> optionalUser = USERREPOSITORY.findByUserId(userId);
            if (optionalUser.isPresent()) {
                user = optionalUser.get(); // 사용자가 존재할 경우 User 객체 가져오기
            } else {
                // 사용자가 존재하지 않을 경우 처리 (예: null 반환 또는 예외 발생)
                System.out.println("User not found for userId: " + userId);
            }
        }
        return user;
    }

    // 유저 리스트 용 페이징 처리 로직
    @Transactional(readOnly = true)
    public Page<UserForm> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // 인덱스 값이라 보일 값보다 -1 해줘야함
        int pageLimit = 8; // 한 페이지에 보여줄 글 갯수
        // 한 페이지당 8개씩 글을 보여주고 정렬 기준은 userId 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        // 모든 보드들을 페이징 처리
        Page<User> users =
                USERREPOSITORY.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "userId")));
        return users.map(user -> new UserForm(user.getUserId(), user.getNickname()));
    }

}