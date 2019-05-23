package com.example.map.mapper;

import com.example.map.domain.Point;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface PointMapper {

    @Select("select * from map_point where longitude = #{longitude} and latitude = #{latitude} " +
            "AND isLock = 0")
    @Results(id = "pointMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "longitude", property = "longitude"),
            @Result(column = "latitude", property = "latitude"),
            @Result(column = "create_at", property = "createAt"),
            @Result(column = "create_by", property = "createBy")
    })
    Point findPointByLongitudeAndLatitude(@Param("longitude") double longitude,
                                          @Param("latitude") double latitude);

    @Insert("insert into map_point(name, longitude, latitude, create_at, create_by) " +
            "values(#{name}, #{longitude}, #{latitude}, #{createAt}, #{createBy})")
    int savePoint(Point point);

    @Select("select * from map_point where longitude < #{x1} and longitude > #{x2} " +
            "and latitude < #{y1} and latitude > #{y2} AND isLock = 0")
    @ResultMap("pointMap")
    List<Point> findPoints(@Param("x1") double x1,
                           @Param("x2") double x2,
                           @Param("y1") double y1,
                           @Param("y2") double y2);

    @Select("select * from map_point where longitude = #{longitude} and latitude = #{latitude} " +
            "AND isLock = 0")
    @ResultMap("pointMap")
    Point findPointByXY(@Param("longitude") double longitude,
                        @Param("latitude") double latitude);

    @Select("select * from map_point where id = #{id} AND isLock = 0")
    @ResultMap("pointMap")
    Point findPointById(int id);

    @Select("SELECT COUNT(*) FROM map_point WHERE id = #{pointId}")
    boolean isExistPoint(int pointId);

    @Update("UPDATE map_point SET isLock = 1 WHERE id = #{pointId}")
    boolean lockPoint(int pointId);

    @Update("UPDATE map_point SET isLock = 0 WHERE id = #{pointId}")
    boolean unlockPoint(int pointId);

    @Select("SELECT isLock FROM map_point WHERE id = #{pointId}")
    int isLockedPoint(int pointId);
}
