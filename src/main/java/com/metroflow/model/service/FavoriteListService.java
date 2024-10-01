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

    public void addFavorite(String userId, String stationName) {
        List<SubwayStation> stations = favoriteListRepository.findStationByName(stationName);

//        for (SubwayStation station : stations) {
//            // 존재하지 않는 경우에만 추가
//            if (!favoriteListRepository.existsByUserIdAndStationId(userId, station.getId())) {
//                FavoriteList favoriteList = new FavoriteList();
//                favoriteList.setUserId(userId);
//                favoriteList.setStation(station);
//                favoriteListRepository.save(favoriteList);
//            }
//        }
    }

}
