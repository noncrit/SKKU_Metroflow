package com.metroflow.controller;

import com.metroflow.model.service.UserService;
import com.metroflow.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
}
