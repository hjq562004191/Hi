package com.example.map.mapper;

import com.example.map.domain.Remark;
import com.example.map.model.RemarkModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @author Qiang
 */
@Mapper
public interface RemarkMapper {
    @Insert("insert into remark(info_id, content, create_at, create_by, " +
            "click_count) values(#{infoId}, #{content}, #{createAt}, " +
            "#{createBy}, #{clickCount})")
    int saveRemark(Remark remark);

    @Select("select * from remark where info_id = #{infoId}")
    @Results(id = "remarkMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "content", property = "content"),
            @Result(column = "create_at", property = "createAt"),
            @Result(column = "create_by", property = "createBy",
                one=@One(select = "com.example.map.mapper.UserMapper.findUserModelById")),
            @Result(column = "id", property = "replays",
                many = @Many(select = "com.example.map.mapper.ReplayMapper.selectReplayByCommId")),
            @Result(column = "id", property = "totalReplay",
                one = @One(select = "com.example.map.mapper.ReplayMapper.selectReplayCountByCommId")),
            @Result(column = "click_count", property = "clickCount")
    })
    List<RemarkModel> getRemarks(int infoId);
    @Select("select click_count from remark where id = #{id}")
    int getClickCount(int id);
    @Update("update remark set click_count = #{clickCount}")
    int updateClickClick(int clickCount);
}
