package com.example.map.mapper;

import com.example.map.domain.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Qiang
 */

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM map_admin WHERE id = #{id}")
    Admin getAdminById(int id);

    @Select("SELECT * FROM map_admin WHERE account = #{account}")
    Admin getAdminByAccount(String account);

    @Select("SELECT * FROM map_admin")
    List<Admin> getAllAdmin();

    @Delete("DELETE FROM map_admin WHERE id = #{id}")
    boolean deleteAdmin(int id);

    @Update("UPDATE FROM map_admin set username = {username}, password = #{password} WHERE id = #{id}")
    boolean updateAdmin(@Param("id") int id,@Param("username") String username,
                        @Param("password") String password);

    @Insert("INSERT INTO map_admin(username, password) VALUES(#{username}, #{password})")
    boolean addAdmin(Admin admin);

    @Select("SELECT username FROM map_admin WHERE username = #{username}")
    String isExistUsername(String username);

    @Select("SELECT account FROM map_admin WHERE account = #{account}")
    String isExistAccount(String account);

    @Update("UPDATE map_user set isLock = 1 WHERE id = #{userId}")
    boolean lockUser(int userId);

    @Update("UPDATE map_user set isLock = 0 WHERE id = #{userId}")
    boolean unLockUser(int userId);

}
