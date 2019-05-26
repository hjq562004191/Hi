package com.example.map.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 管理员
 * @author Qiang
 */
public class Admin implements Serializable {

    // Admin id
    private int id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 8, message = "用户名必须在2到8为之间")
    private String username;


    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度必须在6到16个之间")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
