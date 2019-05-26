package com.example.map.mapper;

import com.example.map.domain.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Qiang
 */

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin getAdminById(String id);

    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin getAdminByUsername(String username);

    @Select("SELECT * FROM admin")
    List<Admin> getAllAdmin();

    @Delete("DELETE FROM admin WHERE id = #{id}")
    boolean deleteAdmin(String id);

    @Update("UPDATE FROM admin set username = {username}, password = #{password} WHERE id = #{id}")
    boolean updateAdmin(String id);

    @Insert("INSERT INTO admin(username, password) VALUES(#{username}, #{password})")
    boolean addAdmin(Admin admin);

    @Select("SELECT username FROM admin WHERE username = #{username}")
    String isExist(String username);
}
