package com.example.map.service.impl;

import com.example.map.domain.Admin;
import com.example.map.mapper.AdminMapper;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.AdminService;
import com.example.map.utils.JWTUtils;
import com.example.map.utils.JedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public ResultModel register(Admin admin) {
        if (adminMapper.isExist(admin.getUsername()) != null) {
            return ResultBuilder.getFailure(1, "用户名已经存在");
        }
        // 加密密码
        boolean isSuccess = adminMapper.addAdmin(admin);
        if (isSuccess) {
            return ResultBuilder.getSuccess("用户注册成功");
        } else {
            return ResultBuilder.getFailure(2, "管理员注册失败");
        }
    }

    @Override
    public ResultModel login(Admin admin) {
        // 检查用户是否存在
        if (adminMapper.isExist(admin.getUsername()) == null) {
            return ResultBuilder.getFailure(1, "管理员用户名不存在");
        }
        Admin adminInDb = adminMapper.getAdminByUsername(admin.getUsername());

        if (!adminInDb.getPassword().equals(admin.getPassword())) {
            return ResultBuilder.getFailure(2, "管理员用户名密码错误");
        }
        try {
            String token = JWTUtils.createToken(admin.getId(), admin.getUsername(), "admin");
            JedisUtils.setToken("admin" + admin.getId(), token, 7);
            return ResultBuilder.getSuccess(token, "登录成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.getFailure(3, "创建token错误");
        }
    }

    @Override
    public String login(Admin admin, Map<String, String> errors) {
        Admin adminInDatabase = adminMapper.getAdminByUsername(admin.getUsername());
        if (adminInDatabase == null) {
            errors.put("admin", "管理员账户不存在");
        }
//        if ()
        return null;
    }
}
