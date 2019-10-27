package com.example.map.mapper;

import com.example.map.domain.ChangePassword;
import com.example.map.domain.User;
import com.example.map.model.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {

    @Update("update map_user SET password=#{password} where account=#{account}")
    boolean changePass(ChangePassword newpass);

    @Select("select password from map_user where account = #{account}")
    String findPasswordByAccount(String account);

    @Insert("insert into map_user(username, account, password, type, image) values(#{username}, #{account}," +
            "#{password}, #{type}, #{image})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    int saveUser(User user);
    @Select("select * from map_user where account = #{account}")
    User findUserByAccount(String account);

    @Select("SELECT username FROM map_user WHERE id = #{id}")
    String findUsernameById(int id);

    @Select("SELECT * from map_user where id = #{userId}")
    User findUserById(int userId);

    @Select("SELECT * from map_user where id = #{userId}")
    UserModel findUserModelById(int userId);

    @Select("UPDATE map_user SET image = #{image} where id = #{id}")
    void saveIcon(User user);

    @Select("SELECT * FROM map_user")
    List<User> getUsers();

    @Select("select image from map_user where id = #{userid}")
    String getimageById(int userid);

    @Update("UPDATE FROM map_user SET username = #{username}, " +
            "password = #{password} WHERE id = #{id}")
    User updateUser(User user);

    @Delete("DELETE FROM map_user WHERE id = #{id}")
    boolean deleteUser(int id);

    @Update("UPDATE map_user set image=#{image} WHERE id = #{userId}")
    boolean updateImage(@Param("userId") int userId, @Param("image") String image);

    @Select("SELECT * FROM map_user WHERE username = #{username}")
    User findUserByUsername(String username);
}
