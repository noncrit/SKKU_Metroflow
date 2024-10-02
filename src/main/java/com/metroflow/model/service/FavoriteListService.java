package com.metroflow.model.service;

import com.metroflow.model.dto.FavoriteList;
import com.metroflow.model.dto.SubwayStation;
import com.metroflow.repository.FavoriteListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteListService {

    @Autowired
    private FavoriteListRepository favoriteListRepository;

    // 특정 역이 즐겨찾기에 등록되어 있는지 확인
    public boolean isFavorite(String userId, String stationName) {
        return favoriteListRepository.isFavorite(userId, stationName) > 0;
    }

    // 모든 즐겨찾기 리스트 가져오기
    public List<FavoriteList> getAllFavoritesByUser(String userId) {
        return favoriteListRepository.findAllByUserId(userId);
    }

}
