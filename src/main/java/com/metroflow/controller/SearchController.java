package com.metroflow.controller;


import com.metroflow.model.dto.SubwayStation;
import com.metroflow.repository.SubwayStationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {

    @GetMapping("/goSearch")
    public String search(Model model) {
        return "search/search";
    }

    private final SubwayStationRepository subwayStationRepository;

    public SearchController(SubwayStationRepository subwayStationRepository) {
        this.subwayStationRepository = subwayStationRepository;
    }

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

        // 모델에 쿼리 파라미터 값을 전달
        model.addAttribute("station", station);
        model.addAttribute("ampm", ampm);
        model.addAttribute("hour", hour);
        model.addAttribute("minute", minute);

        // 결과 페이지로 이동
        return "search/searchResult";
    }
}
