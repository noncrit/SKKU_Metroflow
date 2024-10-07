package com.metroflow.controller;

import com.metroflow.model.dao.NoticeBoardDAO;
import com.metroflow.repository.BoardRepository;
import com.metroflow.repository.NoticeBoardRepository;
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
public class AdminController {

    private final BoardRepository BOARDREPOSITORY;
    private final NoticeBoardDAO NOTICEBOARDDAO;
    private final NoticeBoardRepository NOTICEBOARDREPOSITORY;

    @GetMapping("/admin/board/delete")
    public ResponseEntity<String> boardDelete(@RequestParam("boardNo") Long boardNo) {
        BOARDREPOSITORY.deleteById(boardNo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/board/delete"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // redirection을 하겠다는 의미, 이것만 있으면 작동 X, js에서 다뤄줘야 함(response.redirected)
    }

    @GetMapping("/admin/notice/insert")
    public ResponseEntity<String> noticeInsert(@RequestParam("boardNo") Long boardNo) {
        NOTICEBOARDDAO.insert(boardNo);
        BOARDREPOSITORY.updateBoardByBoardNo(true, boardNo);
        return ResponseEntity.ok().body("게시글 인서트 성공!");
    }

    @GetMapping("/admin/notice/delete")
    public ResponseEntity<String> noticeDelete(@RequestParam("boardNo") Long boardNo) {
        NOTICEBOARDREPOSITORY.deleteById(boardNo);
        BOARDREPOSITORY.updateBoardByBoardNo(false, boardNo);
        return ResponseEntity.ok().body("공지 삭제 성공!");
    }
}
