package com.metroflow.model.service;

import com.metroflow.mapper.SubwayMapMapper;
import com.metroflow.model.dto.SubwayMapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SubwayMapService {

    @Autowired
    private SubwayMapMapper subwayMapMapper;

    private final IsHolidaysService isHolidaysService;

    @Autowired
    private SubwayMapService(IsHolidaysService isHolidaysService) {
        this.isHolidaysService = isHolidaysService;
    }

    public List<SubwayMapInfo> getSubwayMapInfo(String stationName){
        String currentColumn = getCurrentTimeColumn();
        String dayType = getCurrentDayType();
        return subwayMapMapper.getSubwayMapInfo(stationName, dayType, currentColumn);
    }

    //현재 시간을 LocalTime으로 받아와서 h0000 형식으로 반환하는 메소드
    private String getCurrentTimeColumn(){
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();

        //30분 단위로 자르기
        if(minute>=30){
            minute=30;
        }
        else{
            minute=0;
        }

        return String.format("h%02d%02d", hour, minute);
    }
    // 평일, 휴일인지 분류해서 String으로 리턴하는 메소드
    private String getCurrentDayType(){
        LocalDate now = LocalDate.now();
        String result = isHolidaysService.classifyDate(now);
//        System.out.println("subwayMapService getCurrentDayType : " + result);
        return result;
    }

}
