package com.metroflow.repository;

import com.metroflow.model.dto.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {
}
