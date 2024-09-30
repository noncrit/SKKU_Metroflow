package com.metroflow.model.service;

import com.metroflow.mapper.SearchMapper;
import com.metroflow.model.dto.SearchResultInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final SearchMapper searchMapper;

    // SearchMapper 주입 (생성자 주입)
    public SearchService(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    // 데이터를 가져오는 Service 메서드
    public List<SearchResultInfo> getSearchResultInfo(String stationName, String dayType, String currentColumn) {
        return searchMapper.getSearchResultInfo(stationName, dayType, currentColumn);
    }
}
