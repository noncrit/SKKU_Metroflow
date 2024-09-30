package com.metroflow.controller;


import com.metroflow.model.dto.SubwayStation;
import com.metroflow.repository.SubwayStationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
