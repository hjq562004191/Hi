package com.example.map.service;

import com.example.map.domain.ChangePassword;
import com.example.map.domain.User;
import com.example.map.model.ResultModel;
import com.github.pagehelper.PageInfo;


/**
 * @author Qiang
 */
public interface UserService {
    ResultModel register(User user, String code);

    ResultModel login(String account, String password) throws Exception;

    ResultModel getUserMessageById(int userId);

    String getUserIconById(int userId);

    ResultModel saveIcon(int userId, String path);

    User findUserById(int userId);

    User findUserByUsername(String username);

    PageInfo<User> getUsers(int pageNo, int pageSize);

    ResultModel updateUser(User user);

    ResultModel changePass(ChangePassword newpass);

    ResultModel deleteUser(int userId);


}
