package com.example.map.service;


import com.example.map.domain.Admin;
import com.example.map.model.ResultModel;

/**
 * @author Qiang
 */
public interface AdminService {

    ResultModel register(Admin admin, String code);

    ResultModel login(Admin admin);

    ResultModel updata(String username,String password,int id);

    ResultModel lockedUser(int userId);

    ResultModel unLockUser(int userId);
}
