package com.example.map.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ChangePassword {

    String account;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度必须在6到16个之间")
    private String password;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度必须在6到16个之间")
    private String sedpassword;

    public ChangePassword(String account, @NotBlank(message = "密码不能为空") @Length(min = 6, max = 16, message = "密码长度必须在6到16个之间") String password, @NotBlank(message = "密码不能为空") @Length(min = 6, max = 16, message = "密码长度必须在6到16个之间") String sedpassword) {
        this.account = account;
        this.password = password;
        this.sedpassword = sedpassword;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSedpassword() {
        return sedpassword;
    }

    public void setSedpassword(String sedpassword) {
        this.sedpassword = sedpassword;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", sedpassword='" + sedpassword + '\'' +
                '}';
    }
}
