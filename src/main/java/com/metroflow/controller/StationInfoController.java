package com.metroflow.controller;

import com.metroflow.model.dto.SubwayMapInfo;
import com.metroflow.model.service.SubwayMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StationInfoController {

    @Autowired
    private SubwayMapService subwayMapService;

    private static final Logger logger = LoggerFactory.getLogger(StationInfoController.class);

//    @GetMapping("/station-info")
//    public ResponseEntity<SubwayMapInfo> getStationInfo(@RequestParam String stationName) {
//        List<SubwayMapInfo> stationInfoList = subwayMapService.getSubwayMapInfo(stationName);
//        logger.info("stationInfoList: {}", stationInfoList);
//        if (!stationInfoList.isEmpty()) {
//            SubwayMapInfo info = stationInfoList.get(0); // 예시로 첫 번째 데이터 사용
//            return ResponseEntity.ok(info); // SubwayMapInfo 객체를 직접 반환
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/station-info")
    public ResponseEntity<List<SubwayMapInfo>> getStationInfo(@RequestParam String stationName) {
        List<SubwayMapInfo> stationInfoList = subwayMapService.getSubwayMapInfo(stationName);
        logger.info("stationInfoList: {}", stationInfoList);

        if (!stationInfoList.isEmpty()) {
            return ResponseEntity.ok(stationInfoList); // 전체 리스트를 JSON 형태로 반환
        }
        return ResponseEntity.notFound().build();
    }
}
