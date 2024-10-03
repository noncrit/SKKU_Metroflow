package com.metroflow.controller;

import com.metroflow.model.dto.AddToFavoriteApiResponse;
import com.metroflow.model.dto.AddToFavoriteRequest;
import com.metroflow.model.service.FavoriteListService;
import com.metroflow.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteListController {

    @Autowired
    private FavoriteListService favoriteListService;
    @Autowired
    private UserService userService;

    @PostMapping("/addToFavorite")
    public ResponseEntity<AddToFavoriteApiResponse<String>> addToFavorite(@RequestBody AddToFavoriteRequest request) {
        String request_user_id = userService.getUserObject().getUserId();
        long request_station_id= request.getStation_id();

        System.out.println("즐겨찾기 등록 대상 유저 ID : "+request_user_id);
        System.out.println("대상 역 id : "+request_station_id);

        // request_user_id가 null이 아닌 경우에만 실행
        if (request_user_id != null) {
            favoriteListService.addToFavorites(request_user_id, request_station_id);
            return ResponseEntity.ok(new AddToFavoriteApiResponse<>("즐겨찾기 추가 완료", null, true));
        } else {
            return ResponseEntity.badRequest().body(new AddToFavoriteApiResponse<>("유저 ID가 null입니다.", null, false));
        }

    }

}
