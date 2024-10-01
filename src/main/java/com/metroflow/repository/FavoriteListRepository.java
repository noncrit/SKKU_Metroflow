package com.metroflow.repository;

import com.metroflow.model.dto.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;
//변경사항 생성용 주석
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {
}
