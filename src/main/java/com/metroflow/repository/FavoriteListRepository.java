package com.metroflow.repository;

import com.metroflow.model.dto.FavoriteList;
import com.metroflow.model.dto.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {

    // 즐겨찾는 역에 등록된 역인지 확인하는 쿼리
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN 1 ELSE 0 END FROM FavoriteList f " +
            "JOIN f.station s WHERE f.user.userId = :userId AND s.stationName = :stationName")
    int isFavorite(@Param("userId") String userId, @Param("stationName") String stationName);

    // 유저 id 기준으로 즐겨찾기 데이터를 모두 가져오는 쿼리
    @Query("SELECT f FROM FavoriteList f WHERE f.user.userId = :userId")
    List<FavoriteList> findAllByUserId(@Param("userId") String userId);

}
