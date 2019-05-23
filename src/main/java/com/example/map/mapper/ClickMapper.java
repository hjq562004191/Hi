package com.example.map.mapper;

import com.example.map.model.ClickModel;
import org.apache.ibatis.annotations.*;
@Mapper
public interface ClickMapper {

    @Insert("insert into click(user_id, type, info_or_remark) values(#{userId}," +
            " #{type}, #{infoOrRemark})")
    int saveClick(ClickModel clickModel);

    @Delete("DELETE FROM click WHERE user_id = #{userId} AND type = #{type} " +
            "AND info_or_remark = #{infoOrRemark}")
    int deleteClick(ClickModel clickModel);

    @Select("select count(id) from click where type = #{type} and user_id = #{userId} " +
            "and info_or_remark = #{infoOrRemark}")
    boolean isExist(@Param("userId") int userId,
                    @Param("type") int type,
                    @Param("infoOrRemark") int infoOrRemark);
}
