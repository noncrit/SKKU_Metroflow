package com.metroflow.mapper;

import com.metroflow.model.dto.SearchResultInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("SELECT s.station_id, s.station_name, s.station_line, ty.direction_type, ${inputColumn} AS congestion " +
            "FROM station s " +
            "JOIN type ty ON ty.station_id = s.station_id " +
            "JOIN time ti ON ti.type_id = ty.type_id " +
            "WHERE s.station_name = #{stationName} AND ty.day_type = #{dayType}")
    List<SearchResultInfo> getSearchResultInfo(@Param("stationName") String stationName,
                                                        @Param("dayType") String dayType,
                                                        @Param("currentColumn") String currentColumn);

}
