package com.metroflow.controller;

import com.metroflow.model.dto.FavoriteApiResponse;
import com.metroflow.model.dto.FavoriteRequest;
import com.metroflow.model.dto.NoticeBoard;
import com.metroflow.model.service.FavoriteListService;
import com.metroflow.model.service.NoticeBoardService;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FavoriteListController {

    @Autowired
    private FavoriteListService favoriteListService;
    @Autowired
    private UserService userService;

    private final UserService USERSERVICE;
    private final NoticeBoardRepository NOTICEBOARDREPOSITORY;
    private final NoticeBoardService NOTICEBOARDSERVICE;

    @PostMapping("/addToFavorite")
        public ResponseEntity<FavoriteApiResponse<String>> addToFavorite(@RequestBody FavoriteRequest request) {
        String request_user_id = userService.getUserObject().getUserId();
        long request_station_id= request.getStation_id();

        System.out.println("즐겨찾기 등록 대상 유저 ID : "+request_user_id);
        System.out.println("대상 역 id : "+request_station_id);

        // request_user_id가 null이 아닌 경우에만 실행
        if (request_user_id != null) {
            favoriteListService.addToFavorites(request_user_id, request_station_id);
            return ResponseEntity.ok(new FavoriteApiResponse<>("즐겨찾기 추가 완료", null, true));
        } else {
            return ResponseEntity.badRequest().body(new FavoriteApiResponse<>("유저 ID가 null입니다.", null, false));
        }

    }

    @PostMapping("/deleteFromFavoriteList")
    public ResponseEntity<FavoriteApiResponse<String>> deleteFromFavoriteList(@RequestBody FavoriteRequest request){
        String request_user_id = userService.getUserObject().getUserId();
        long request_station_id= request.getStation_id();

        System.out.println("즐겨찾기 삭제 대상 유저 ID : "+request_user_id);
        System.out.println("삭제 대상 역 id : "+request_station_id);

        // request_user_id가 null이 아닌 경우에만 실행
        if (request_user_id != null) {
            favoriteListService.deleteFromFavorites(request_user_id, request_station_id);
            return ResponseEntity.ok(new FavoriteApiResponse<>("즐겨찾기 삭제 완료", null, true));
        } else {
            return ResponseEntity.badRequest().body(new FavoriteApiResponse<>("유저 ID가 null입니다.", null, false));
        }
    }

    @GetMapping("/goFavoriteList")
    public String goFavoriteList(Model model){
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        List<NoticeBoard> notices = NOTICEBOARDREPOSITORY.findAll(Sort.by(Sort.Direction.DESC, "boardNo"));
        for (NoticeBoard notice : notices) {
            String contents = NOTICEBOARDSERVICE.modifyBoardText(notice.getBoardNo());
            notice.getBoard().setBoardText(contents);
        }
        model.addAttribute("notices", notices);
        return "favoriteList/favoriteList";
    }
}
