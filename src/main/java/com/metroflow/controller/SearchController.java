package com.metroflow.controller;


import com.metroflow.model.dto.SearchResultInfo;
import com.metroflow.model.dto.SubwayStation;
import com.metroflow.model.service.IsHolidaysService;
import com.metroflow.model.service.SearchService;
import com.metroflow.model.service.UserService;
import com.metroflow.repository.SubwayStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final IsHolidaysService isHolidaysService;
    private final UserService USERSERVICE;
    private final SearchService searchService;
//    private void SearchService(IsHolidaysService isHolidaysService){
//        this.isHolidaysService = isHolidaysService;
//    }



    @GetMapping("/goSearch")
    public String search(Model model) {
        model.addAttribute("sessionUser", USERSERVICE.getUserObject());
        return "search/search";
    }

    private final SubwayStationRepository subwayStationRepository;



    @ResponseBody
    @GetMapping("/goSearch/stations")
    public List<SubwayStation> getSubwayStations(String search) {
        return subwayStationRepository.findByStationNameContainingIgnoreCase(search);
    }

    @GetMapping("/goSearch/result")
    public String getSearchResult(
            @RequestParam(value = "station", required = false) String station,
            @RequestParam(value = "ampm", required = false) String ampm,
            @RequestParam(value = "hour", required = false) String hour,
            @RequestParam(value = "minute", required = false) String minute,
            Model model) {

        LocalDate currentDate = LocalDate.now();

        // dayType 설정 (평일 또는 휴일 계산)
        String dayType = isHolidaysService.classifyDate(currentDate);


        String inputColumn = getInputColumn(ampm, hour, minute);

        // Service를 통해 데이터 조회
        List<SearchResultInfo> searchResults = searchService.getSearchResultInfo(station, dayType, inputColumn);

        // 모델에 데이터 추가
        model.addAttribute("stationData", searchResults);
        model.addAttribute("station", station);
        model.addAttribute("ampm", ampm);
        model.addAttribute("hour", hour);
        model.addAttribute("min", minute);

        // 결과 페이지로 이동
        return "search/searchResult";
    }

    // 현재 열(column)을 계산하는 메서드
    public String getInputColumn(String ampm, String hour, String minute) {
        // hour와 minute 값의 정수형 변환
        int hourInt = Integer.parseInt(hour);
        int minuteInt = Integer.parseInt(minute);

        // am/pm에 따른 시간 계산
        if ("pm".equalsIgnoreCase(ampm) && hourInt < 12) {
            hourInt += 12;  // PM이면 12를 더함
        } else if ("am".equalsIgnoreCase(ampm) && hourInt == 12) {
            hourInt = 0;  // AM 12시는 00시로 변경
        }

        // hour와 minute을 두 자리로 맞추고 4자리 시간 문자열로 생성
        String formattedHour = String.format("%02d", hourInt);  // 두 자리로 맞춤
        String formattedMinute = String.format("%02d", minuteInt);  // 두 자리로 맞춤

        // 최종적으로 hour와 minute을 결합하여 4자리 시간 형식으로 반환
        return formattedHour + formattedMinute;
    }


}
