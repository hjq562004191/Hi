package com.example.map.mapper;

import com.example.map.domain.Information;
import com.example.map.model.InformationModel;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @author Qiang
 */
@Mapper
public interface InformationMapper {

    @Insert("insert into information(point_id, type, content, remark_count, click_count, " +
            "user_id, create_at) values(#{pointId}, #{type}, #{content}, #{remarkCount}, " +
            "#{clickCount}, #{userId}, #{createAt})")
    int saveMessage(Information information);

    @Select("select * from information where point_id = #{pointId} and type = #{type} " +
            "AND isLock = 0")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "point_Id", property = "pointId"),
            @Result(column = "type", property = "type"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "remark_count", property = "remarkCount"),
            @Result(column = "click_count", property = "clickCount"),
            @Result(column = "create_at", property = "createAt")
    })
    List<InformationModel> findInformation(@Param("pointId") int pointId,
                                           @Param("type") int type);


    @Select("select * from information where id = #{id} AND isLock = 0")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "point_Id", property = "pointId"),
            @Result(column = "type", property = "type"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "remark_count", property = "remarkCount"),
            @Result(column = "click_count", property = "clickCount"),
            @Result(column = "create_at", property = "createAt"),
    })
    InformationModel findById(int id);



    @Update("update information set click_count = #{clickCount}, remark_count = #{remarkCount}" +
            " where id = #{id}")
    int updaateClickOrRemark(InformationModel informationModel);

    @Delete("DELETE  FROM information WHERE id = #{id}")
    void delete(int id);

    @Select("SELECT isLock FROM information WHERE id = #{infoId}")
    int isLockInformation(int infoId);

    @Update("UPDATE information SET isLock = 1 WHERE id = #{infoId}")
    boolean lockInformation(int infoId);

    @Update("UPDATE information SET isLock = 0 WHERE id = #{infoId}")
    boolean unLockInformation(int infoId);

    @Select("SELECT COUNT(*) FROM information WHERE id = #{infoId} ")
    boolean isExist(int infoId);
}
