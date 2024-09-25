package com.metroflow.mapper;

import com.metroflow.model.dto.SubwayMapInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubwayMapMapper {
    //currentColumns은 현재 시간을 time column 형식(ex h0530) 형식으로 바꿔서 가져옴
    @Select("SELECT s.station_id, s.station_line, ty.direction_type, ${currentColumn} AS timeValue " +
            "FROM station s " +
            "JOIN type ty ON ty.station_id = s.station_id " +
            "JOIN time ti ON ti.type_id = ty.type_id " +
            "WHERE s.station_name = #{stationName} AND ty.day_type = #{dayType}")
    List<SubwayMapInfo> getSubwayMapInfo(@Param("stationName") String stationName,
                                     @Param("dayType") String dayType,
                                     @Param("currentColumn") String currentColumn);
}
