package com.metroflow.model.service;

import com.metroflow.model.dto.FavoriteList;
import com.metroflow.model.dto.FavoriteListPageDto;
import com.metroflow.model.dto.SubwayStation;
import com.metroflow.model.dto.User;
import com.metroflow.repository.FavoriteListRepository;
import com.metroflow.repository.SubwayStationRepository;
import com.metroflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // user_id, stationName 기반으로 즐겨찾기에 등록된 역들의 id 반환(List<Long> 반환)
    public List<Long> getFavoriteListStationIds(String userId, String stationName){
        return favoriteListRepository.getFavoriteListStationIds(userId, stationName);
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
            if (favoriteListRepository.checkisDuplicateFavoriteList(user.getUserId(), station.getStationId()).isEmpty()) {
                // 즐겨찾기에 추가
                FavoriteList favoriteList = new FavoriteList();
                favoriteList.setUser(user);
                favoriteList.setStation(station);
                favoriteListRepository.save(favoriteList);
            }
        }

    }

    @Transactional
    public void deleteFromFavorites(String userId, Long stationId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<SubwayStation> stationOptional = subwayStationRepository.findById(stationId);
        List<FavoriteList> favorites = favoriteListRepository.findByUser_UserIdAndStation_StationId(userId, stationId);

        System.out.println("User ID: " + userId);
        System.out.println("Station ID: " + stationId);
        System.out.println("Favorites: " + favorites);

        if (userOptional.isPresent() && stationOptional.isPresent()) {
            // user와 station이 존재하는 경우
            if (!favorites.isEmpty()) { // favorites가 비어있지 않을 경우 삭제
                favoriteListRepository.deleteAll(favorites);
            }
        }
    }

    public Page<FavoriteListPageDto> getFavoriteListByUserId(String userId, Pageable pageable){
        return favoriteListRepository.findFavoriteListByUserId(userId, pageable);
    }



}
