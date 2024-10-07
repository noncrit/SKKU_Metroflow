package com.metroflow.controller;

import com.metroflow.model.dto.FavoriteApiResponse;
import com.metroflow.model.dto.FavoriteListPageDto;
import com.metroflow.model.dto.FavoriteRequest;
import com.metroflow.model.dto.NoticeBoard;
import com.metroflow.model.service.FavoriteListService;
import com.metroflow.model.service.NoticeBoardService;
import com.metroflow.model.service.SubwayMapService;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private SubwayMapService subwayMapService;

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

    // 메뉴바에서 a 태그 버튼 눌렀을 경우
    @GetMapping("/goFavoriteList")
    public String goFavoriteList(Model model){

        String favoriteList_user_id = userService.getUserObject().getUserId();

        if(favoriteList_user_id != null){
            int page = 0;
            int size = 10;
            Pageable pageable = PageRequest.of(page, size);
            Page<FavoriteListPageDto> favoriteListPage = favoriteListService.getFavoriteListByUserId(favoriteList_user_id,pageable);

            String currentTime = subwayMapService.getCurrentTimeColumn();

            // 콘솔에 페이지 정보 출력
            System.out.println("총 페이지 수: " + favoriteListPage.getTotalPages());
            System.out.println("현재 페이지 번호: " + favoriteListPage.getNumber());

            favoriteListPage.getContent().forEach(item -> {
                System.out.println("User ID: " + item.getUserId());
                System.out.println("Station ID: " + item.getStationId());
                System.out.println("Station Line: " + item.getStationLine());
                System.out.println("Station Name: " + item.getStationName());
                System.out.println("Direction Type: " + item.getDirectionType());
                System.out.println("Day Type: " + item.getDayType());
                System.out.println("Current Time Congestion : "+ item.getSubwayTime().getH1530());
            });


            // 모델에 데이터 추가
            model.addAttribute("currentTime", currentTime);

            model.addAttribute("favoriteList", favoriteListPage.getContent()); // 페이지의 내용
            model.addAttribute("totalPages", favoriteListPage.getTotalPages()); // 총 페이지 수
            model.addAttribute("currentPage", favoriteListPage.getNumber()); // 현재 페이지 번호

        }

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
