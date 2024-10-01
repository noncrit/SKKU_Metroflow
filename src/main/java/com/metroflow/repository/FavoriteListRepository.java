package com.metroflow.repository;

import com.metroflow.model.dto.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN 1 ELSE 0 END FROM FavoriteList f " +
            "JOIN f.station s WHERE f.user = :userId AND s.stationName = :stationName")
    Integer isFavorite(@Param("userId") String userId, @Param("stationName") String stationName);
}
