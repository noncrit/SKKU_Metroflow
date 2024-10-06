package com.metroflow.controller;

import com.metroflow.model.dto.UserForm;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class Admincontroller {

    private final UserService USERSERVICE;
    private final BoardRepository BOARDREPOSITORY;

    @GetMapping("/admin/board/delete")
    public ResponseEntity<String> boardDelete(@RequestParam("boardNo") Long boardNo) {
        BOARDREPOSITORY.deleteById(boardNo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/board/delete"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // redirection을 하겠다는 의미, 이것만 있으면 작동 X, js에서 다뤄줘야 함(response.redirected)
    }

    @GetMapping("/admin/userList")
    public String goUserList(Model model, @PageableDefault(page = 1) Pageable pageable) {
        Page<UserForm> userList = USERSERVICE.paging(pageable); // 페이징 처리된 보드들 리스트

        int blockLimit = 5; // 한번에 보일 페이지 갯수 제한
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min((startPage + blockLimit - 1), userList.getTotalPages());

        model.addAttribute("userList", userList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "admin/userList";
    }
}
