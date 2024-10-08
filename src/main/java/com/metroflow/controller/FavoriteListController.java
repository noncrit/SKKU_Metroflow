package com.metroflow.controller;

import com.metroflow.model.dto.*;
import com.metroflow.model.service.*;
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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FavoriteListController {

    @Autowired
    private FavoriteListService favoriteListService;
    @Autowired
    private UserService userService;

    @Autowired
    private SubwayMapService subwayMapService;

    @Autowired
    private IsHolidaysService isHolidaysService;

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
            // 6개 레코드가 완전한 데이터 1개 !!
            int size = 24;
            Pageable pageable = PageRequest.of(page, size);
            Page<FavoriteListPageDto> favoriteListPage = favoriteListService.getFavoriteListByUserId(favoriteList_user_id,pageable);

                        favoriteListPage.getContent().forEach(item -> {
//                System.out.println("User ID: " + item.getUserId());
                System.out.println("Station ID: " + item.getStationId());
                System.out.println("Station Line: " + item.getStationLine());
                System.out.println("Station Name: " + item.getStationName());
                System.out.println("Direction Type: " + item.getDirectionType());
                System.out.println("Day Type: " + item.getDayType());
//                System.out.println("Current Time Congestion : "+ item.getSubwayTime().getH1530());
            });
                        
            // setTime을 인덱스처럼 사용할 예정 (h0530 같은 값으로)
            String setTime = subwayMapService.getCurrentTimeColumn();
            String dayType = subwayMapService.getCurrentDayType();

            // ampm, 시, 분 을 가져오는 기능
            // 페이지 최초 접근시에는 현재 기준으로 설정
            TimeAttributes timeAttributes = isHolidaysService.getCurrentTimeAttributes();
            model.addAttribute("amPm",timeAttributes.getAmPm());
            model.addAttribute("hour",timeAttributes.getHour());
            model.addAttribute("minute",timeAttributes.getMinute());

            // station_id를 이용해 매핑 처리해서 station_id 하위 항목으로 상선, 하선 데이터가 들어가도록 조정
            // 그룹화 (평일, 휴일 기준으로 매칭되는 데이터만 매핑시킴)
            Map<Long, List<FavoriteListPageDto>> groupedFavorites = favoriteListPage.getContent().stream()
                    .filter(item -> item.getDayType().equals(dayType)) // dayType이 일치하는 항목만 필터링
                    .collect(Collectors.groupingBy(FavoriteListPageDto::getStationId));

            // 모델에 데이터 추가
            model.addAttribute("setTime", setTime);
            model.addAttribute("groupedFavorites", groupedFavorites);   // 매핑시킨 데이터
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
