package com.example.map.service.impl;

import com.example.map.domain.Admin;
import com.example.map.mapper.AdminMapper;
import com.example.map.mapper.UserMapper;
import com.example.map.model.ResultBuilder;
import com.example.map.utils.JWTUtils;
import com.example.map.utils.JedisUtils;
import com.example.map.utils.PhoneUtil;
import com.example.map.model.ResultModel;
import com.example.map.service.AdminService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * @author Qiang
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public ResultModel register(Admin admin, String code) {
        if (adminMapper.isExistAccount(admin.getAccount())!= null){
            return ResultBuilder.getFailure(1, "账号已经存在");
        }
        if (adminMapper.isExistUsername(admin.getUsername()) != null) {
            return ResultBuilder.getFailure(2, "用户名已经存在");
        }
        if (PhoneUtil.judgeCodeIsTrue(code, admin.getAccount())){
        // 加密密码
        boolean isSuccess = adminMapper.addAdmin(admin);
        if (isSuccess) {
            return ResultBuilder.getSuccess("用户注册成功");
        } else {
            return ResultBuilder.getFailure(2, "管理员注册失败");
        }
        }else {
            return ResultBuilder.getFailure(-1,"手机验证失败");
        }
    }

    @Override
    public ResultModel login(Admin admin) {
        // 检查用户是否存在
        if (adminMapper.isExistAccount(admin.getAccount()) == null) {
            return ResultBuilder.getFailure(1, "管理员不存在");
        }
        Admin adminInDb = adminMapper.getAdminByAccount(admin.getAccount());

        if (!adminInDb.getPassword().equals(admin.getPassword())) {
            return ResultBuilder.getFailure(2, "管理员账号密码错误");
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
    public ResultModel updata(String username,String password,int id) {
        // 检查用户是否存在
        if (adminMapper.isExistUsername(username) == null) {
            return ResultBuilder.getFailure(1, "管理员不存在");
        }
        if (adminMapper.updateAdmin(id,username,password)){
            return ResultBuilder.getSuccess("更新成功");
        }else {
            return ResultBuilder.getFailure(-1,"更新失败");
        }
    }

    @Override
    public ResultModel lockedUser(int userId) {
        if (userMapper.findUserById(userId) == null) {
            return ResultBuilder.getFailure(1, "用户不存在");
        }
        boolean isLock = adminMapper.lockUser(userId);
        if (isLock) {
            return ResultBuilder.getSuccess("锁定用户成功");
        }
        return ResultBuilder.getFailure(2, "锁定用户失败");
    }

    @Override
    public ResultModel unLockUser(int userId) {
        if (userMapper.findUserById(userId) == null) {
            return ResultBuilder.getFailure(1, "用户不存在");
        }
        boolean isUnLock = adminMapper.unLockUser(userId);
        if (isUnLock) {
            return ResultBuilder.getSuccess("用户解锁成功");
        }
        return ResultBuilder.getFailure(2, "用户解锁失败");
    }


}
