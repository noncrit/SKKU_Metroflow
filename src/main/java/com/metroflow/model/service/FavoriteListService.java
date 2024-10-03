package com.metroflow.model.service;

import com.metroflow.model.dto.FavoriteList;
import com.metroflow.model.dto.SubwayStation;
import com.metroflow.model.dto.User;
import com.metroflow.repository.FavoriteListRepository;
import com.metroflow.repository.SubwayStationRepository;
import com.metroflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteListService {

    @Autowired
    private FavoriteListRepository favoriteListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubwayStationRepository subwayStationRepository;

    // user_id, stationName 기반으로 특정 역이 즐겨찾기에 등록되어 있는지 확인(boolean 값 리턴)
    // 클라이언트 측에서 텍스트 클릭으로 요청을 보내기에 stationName으로 찾아야함
    public boolean isFavorite(String userId, String stationName) {
        return favoriteListRepository.isFavorite(userId, stationName) > 0;
    }

    // 모든 즐겨찾기 리스트 가져오기
    public List<FavoriteList> getAllFavoritesByUser(String userId) {
        return favoriteListRepository.findAllByUserId(userId);
    }

    // 즐겨찾기 등록 서비스
    @Transactional
    public void addToFavorites(String userId, Long stationId) {

        // 사용자와 역이 존재하는지 확인
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<SubwayStation> stationOptional = subwayStationRepository.findById(stationId);

        // 쿼리로 가져온 데이터가 유효해야 작동함
        if (userOptional.isPresent() && stationOptional.isPresent()) {
            User user = userOptional.get();
            SubwayStation station = stationOptional.get();
            // 이미 즐겨찾기에 있는지 확인
            // isempty가 true여야 실행됨
            if (favoriteListRepository.findByUserIdAndStationId(user.getUserId(), station.getStationId()).isEmpty()) {
                // 즐겨찾기에 추가
                FavoriteList favoriteList = new FavoriteList();
                favoriteList.setUser(user);
                favoriteList.setStation(station);
                favoriteListRepository.save(favoriteList);
            }
        }

    }

}
