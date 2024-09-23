package com.metroflow.repository;

import com.metroflow.model.dto.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubwayStationRepository extends JpaRepository<SubwayStation, Long> {
}
