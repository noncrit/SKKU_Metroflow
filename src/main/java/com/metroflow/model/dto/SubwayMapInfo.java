package com.metroflow.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubwayMapInfo {
    private Long stationId;
    private String station_name;
    private String station_line;
    private String direction_type;
    private String timeValue; // 동적으로 가져오는 시간 정보
    // Getters and Setters
}
