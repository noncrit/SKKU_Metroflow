package com.metroflow.model.service;

import com.metroflow.mapper.SubwayMapMapper;
import com.metroflow.model.dto.SubwayMapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class SubwayMapService {

    @Autowired
    private SubwayMapMapper subwayMapMapper;

    public List<SubwayMapInfo> getSubwayMapInfo(String stationName, String dayType){
        String currentColumn = getCurrentTimeColumn();
        return subwayMapMapper.getSubwayMapInfo(stationName, dayType, currentColumn);
    }

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

}
