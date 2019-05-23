package com.example.map.mapper;

import com.example.map.model.ItemsModel;
import org.apache.ibatis.annotations.*;
@Mapper
public interface ItemsMapper {

    @Insert("insert into items values(#{pointId}, #{mesCount}, #{phoCount}, " +
            "#{audCount}, #{vidCount})")
    int saveItems(ItemsModel itemsModel);

    @Select("select * from items where id = #{pointId}")
    @Results({
            @Result(column = "id", property = "pointId"),
            @Result(column = "mes_count", property = "mesCount"),
            @Result(column = "pho_count", property = "phoCount"),
            @Result(column = "aud_count", property = "audCount"),
            @Result(column = "vid_count", property = "vidCount")
    })
    ItemsModel findItemsByPointId(int pointId);
    @Update("update items set mes_count = #{mesCount}, pho_count = #{phoCount}, " +
            "aud_count = #{audCount}, vid_count = #{vidCount} where id = #{pointId}")
    int updateItems(ItemsModel itemsModel);

//    @Insert("insert into items_photo (user_id,items_id,url,title) values (#{userId},#{pointId},#{url},#{title})")
//    int addPhoto(itemsPhoto itemsPhotos);
}
