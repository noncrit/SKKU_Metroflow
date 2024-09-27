package com.metroflow.controller;

import com.metroflow.model.dao.BoardDAO;
import com.metroflow.model.dto.Board;
import com.metroflow.model.dto.BoardDTO;
import com.metroflow.model.dto.Recommendation;
import com.metroflow.model.service.BoardService;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.BoardRepository;
import com.metroflow.repository.RecommendationRepository;
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
    private final RecommendationRepository RECOMMENDATIONREPOSITORY;

    // /board?page=1
    // 보드 페이징 처리 컨트롤러
    @GetMapping("/board")
    public String goBoard(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = BOARDSERVICE.paging(pageable); // 페이징 처리된 보드들 리스트

        int blockLimit = 5; // 한번에 보일 페이지 갯수 제한
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
        return "board/board"; // 보드
    }

    // 보드 작성 공간으로 이동해주는 컨트롤러
    @GetMapping("/board/writeBoard")
    public String goWrite(Model model, Board board) {
        Map<String, List<String>> stationMap =  BOARDSERVICE.getStationNames();
        model.addAttribute("station", stationMap); // 작성란 위의 옵션들에 값을 채워넣기 위함(호선 당 역)
        board.setUser(USERSERVICE.getUserObject());
        model.addAttribute("board", board);
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "board/boardToWrite"; // 보드 작성 공간
    }

    // 보드 작성란에서 작성 후 글쓰기 누를 시 아래 컨트롤러 작동
    @PostMapping("/board/write")
    public String write(@ModelAttribute Board board, Model model) {
        BOARDDAO.writeBoard(board); // insert해주는 DAO
        return "redirect:/board"; // "/board"를 GET방식으로 받는 컨트롤러(goBoard)로 감
    }

    // 보드 제목 클릭 시 해당 보드의 내용으로 가게 해주는 컨트롤러
    @GetMapping("/board/content")
    public String viewContent(@RequestParam("boardNo") Long no, Model model) {
        model.addAttribute("board", BOARDSERVICE.getInfo(no));
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        BOARDREPOSITORY.plusView(no); // 조회수 추가 메소드
        BOARDDAO.insertRecommendation(no); // 추천 테이블속 유저에게 해당하는 테이블 없을 시 생성, 있다면 void 반환
        model.addAttribute("userRec", BOARDSERVICE.getMyRecommendation(no));
        return "board/boardContent"; // 보드 내용
    }

    // 자신의 글을 수정할 때 글 수정버튼 누르면 아래 컨트롤러 작동
    @GetMapping("/board/updateBoard")
    public String goUpdate(@RequestParam("no") Long no, Model model) {
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        model.addAttribute("board", BOARDSERVICE.getInfo(no));
        Map<String, List<String>> stationMap =  BOARDSERVICE.getStationNames();
        model.addAttribute("station", stationMap);
        return "board/boardToWrite"; // 작성 화면
    }

    // 글 수정버튼 누를 때 아래 컨트롤러 작동
    @PostMapping("/board/update")
    public String update(@ModelAttribute("board") Board board, Model model) {
        BOARDREPOSITORY.updateBoard(board.getBoardText(), board.getStationLine(), board.getStationName(), board.getTitle(), board.getBoardNo());
        model.addAttribute("board", BOARDSERVICE.getInfo(board.getBoardNo()));
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        model.addAttribute("userRec", BOARDSERVICE.getMyRecommendation(board.getBoardNo()));
        return "board/boardContent"; // 보드 수정이 완료된 내용 화면
    }

    // 글 삭제버튼 누르면 아래 컨트롤러 작동
    @GetMapping("/board/delete")
    public String delete(@RequestParam("no") Long no, Model model) {
        BOARDREPOSITORY.deleteById(no); // 보드 번호에 맞는 해당 글 삭제
        return "redirect:/board"; // 보드 화면으로 redirect
    }

    // 글 추천
    @GetMapping("/board/recommendation")
    public String recommendation(@RequestParam("url") String url,
                                 @RequestParam("boardNo") Long no,
                                 @RequestParam("isThumbsUp") boolean up,
                                 @RequestParam("isThumbsDown") boolean down,
                                 @RequestParam("priorThumbsUp") boolean priorUp,
                                 @RequestParam("priorThumbsDown") boolean priorDown) {
        System.out.println("priorUp : " + priorUp);
        System.out.println("priorDown : " + priorDown);
        System.out.println("up : " + up);
        System.out.println("down : " + down);
        BOARDDAO.updateRecommendation(no, up, down, priorUp, priorDown);
        if (url.equals("/board/updateBoard") || url.equals("/board/delete")) {
            return "redirect:" + url + "?no=" + no;
        }
        return "redirect:" + url;
    }
}
