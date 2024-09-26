package com.metroflow.controller;

import com.metroflow.model.dao.BoardDAO;
import com.metroflow.model.dto.Board;
import com.metroflow.model.dto.BoardDTO;
import com.metroflow.model.service.BoardService;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService BOARDSERVICE;
    private final UserService USERSERVICE;
    private final BoardDAO BOARDDAO;
    private final BoardRepository BOARDREPOSITORY;

    // /board?page=1
    @GetMapping("/board")
    public String goBoard(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = BOARDSERVICE.paging(pageable); // 페이지 값을 가져오기 위해서

        int blockLimit = 5;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min((startPage + blockLimit - 1), boardList.getTotalPages());
        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3 
        // 현재 사용자가
        // 7 8 9 
        // 보여지는 페이지 갯수 8개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "board/board";
    }

    @GetMapping("/board/writeBoard")
    public String goWrite(Model model, Board board) {
        Map<String, List<String>> stationMap =  BOARDSERVICE.getStationNames();
        model.addAttribute("station", stationMap);
        board.setUser(USERSERVICE.getUserObject());
        model.addAttribute("board", board);
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "board/boardToWrite";
    }

    @PostMapping("/board/write")
    public String write(@ModelAttribute Board board, Model model) {
        BOARDDAO.writeBoard(board);
        return "redirect:/board";
    }

    @GetMapping("/board/content")
    public String viewContent(@RequestParam("boardNo") Long no, Model model) {
        model.addAttribute("board", BOARDSERVICE.getInfo(no));
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        BOARDREPOSITORY.plusView(no);
        return "board/boardContent";
    }

    @GetMapping("/board/updateBoard")
    public String goUpdate(@RequestParam("no") Long no, Model model) {
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        model.addAttribute("board", BOARDSERVICE.getInfo(no));
        Map<String, List<String>> stationMap =  BOARDSERVICE.getStationNames();
        model.addAttribute("station", stationMap);
        return "board/boardToWrite";
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute("board") Board board, Model model) {
        BOARDREPOSITORY.updateBoard(board.getBoardText(), board.getStationLine(), board.getStationName(), board.getTitle(), board.getBoardNo());
        model.addAttribute("board", BOARDSERVICE.getInfo(board.getBoardNo()));
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "board/boardContent";
    }

    @GetMapping("/board/delete")
    public String delete(@RequestParam("no") Long no, Model model) {
        BOARDREPOSITORY.deleteById(no);
        return "redirect:/board";
    }
}
