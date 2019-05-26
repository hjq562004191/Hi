package com.example.map.mapper;

import com.example.map.domain.Replay;
import com.example.map.model.ReplayModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ReplayMapper {

    @Insert("INSERT INTO replay(comm_id, from_id, to_id, create_at,content)" +
            " VALUES(#{commId}, #{fromId}, #{toId}, #{createAt},#{content})")
    Integer save(Replay replay);

    @Update("UPDATE replay SET click = #{click} WHERE id = #{replayId}")
    Integer addClick(@Param("replayId") Integer replayId,
                     @Param("click") Integer click);

    @Select("SELECT click FROM replay WHERE id = #{id}")
    Integer selectClickCountById(Integer id);

    @Select("SELECT * FROM replay WHERE comm_id = #{commId} LIMIT 3")
    @Results(id = "replayMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "comm_id", property = "commId"),
            @Result(column = "from_id", property = "fromUser",
                one = @One(select = "com.example.map.mapper.UserMapper.findUserModelById")),
            @Result(column = "to_id", property = "toUser",
                one = @One(select = "com.example.map.mapper.UserMapper.findUserModelById")),
            @Result(column = "content", property = "content"),
            @Result(column = "create_at", property = "createAt"),
            @Result(column = "click", property = "click")
    })
    List<ReplayModel> selectReplayByCommId(Integer commId);

    @Select("SELECT * FROM replay WHERE comm_id = #{commId}")
    @ResultMap("replayMap")
    List<ReplayModel> selectAllByCommId(Integer commId);

    @Select("SELECT COUNT(*) FROM replay WHERE comm_id = #{commId}")
    Integer selectReplayCountByCommId(Integer commId);

    @Delete("DELETE FROM replay WHERE id = #{id}")
    Integer deleteById(Integer id);
}
