package com.example.map.service.impl;

import com.example.map.domain.ChangePassword;
import com.example.map.domain.User;
import com.example.map.mapper.UserMapper;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.UserService;
import com.example.map.utils.JWTUtils;
import com.example.map.utils.JedisUtils;
import com.example.map.utils.PhoneUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Qiang
 */
@Service
public class UserServiceImpl implements UserService {
    Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Resource
    UserMapper userMapper;

    @Override
    public ResultModel register(User user, String code) {
        user.setType("user");
        // 账号没被注册过
        if (userMapper.findUserByAccount(user.getAccount()) == null) {
            logger.info(user);
            user.setType("user");
            try {
                if (PhoneUtil.judgeCodeIsTrue(code,user.getAccount())) {
                    userMapper.saveUser(user);
                }else {
                    return ResultBuilder.getFailure(3, "手机验证码错误");
                }
            } catch (Exception e) {
                return ResultBuilder.getFailure(2, "未知错误");
            }
            return ResultBuilder.getSuccess("注册成功");
        } else {
            return ResultBuilder.getFailure(1, "该账号已被注册");
        }
    }

    @Override
    public ResultModel login(String account, String password) {
        User user = userMapper.findUserByAccount(account);
        if (user == null) {
            return ResultBuilder.getFailure(1, "用户不存在");
        }
        if (user.getIsLock() == 1) {
            return ResultBuilder.getFailure(4, "该用户被锁定");
        }
        String mPassword = userMapper.findPasswordByAccount(account);
        if (mPassword.equals(password)) {
            String token = null;
            try {
                token = JWTUtils.createToken(user.getId(), user.getUsername(), user.getType());
                JedisUtils.setToken(String.valueOf(user.getId()), token, 7);
            } catch (Exception e) {
                logger.info("创建token错误,Redis运行失败");
                e.printStackTrace();
                return ResultBuilder.getFailure(3, "创建token错误");
            }
            return ResultBuilder.getSuccess(token, "登录成功");
        } else {
            return ResultBuilder.getFailure(2, "密码错误");
        }
    }



    @Override
    public ResultModel getUserMessageById(int userId) {
        User user = userMapper.findUserById(userId);
        if (user == null) {
            logger.info("用户不存在");
            return ResultBuilder.getFailure(1, "用户不存在");
        } else {
            return ResultBuilder.getSuccess(user);
        }
    }

    @Override
    public ResultModel saveIcon(int userId, String path) {
        User user = userMapper.findUserById(userId);
        user.setImage(path);
        userMapper.saveIcon(user);
        return ResultBuilder.getSuccess(user);
    }


    @Override
    public User findUserById(int userId) {
        return userMapper.findUserById(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public PageInfo<User> getUsers(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userMapper.getUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public ResultModel updateUser(User user) {
        User isExistUser = userMapper.findUserById(user.getId());
        if (isExistUser == null) {
            return ResultBuilder.getFailure(1, "用户不存在");
        }
        User isUpdateUser = userMapper.updateUser(user);
        if (isUpdateUser == null) {
            return ResultBuilder.getFailure(2, "用户信息更新失败");
        }
        return ResultBuilder.getSuccess("用户信息更新成功");
    }

    @Override
    public ResultModel changePass(ChangePassword newpass) {
        if (userMapper.changePass(newpass)) {
            return ResultBuilder.getSuccess("修改成功");
        }else {
            return ResultBuilder.getFailure(-2,"jdbc修改失败");
        }
    }

    @Override
    public ResultModel deleteUser(int userId) {
        User isExistUser = userMapper.findUserById(userId);
        if (isExistUser == null) {
            return ResultBuilder.getFailure(1, "用户不存在");
        }
        boolean isDelete = userMapper.deleteUser(userId);
        if (isDelete == false) {
            return ResultBuilder.getFailure(2, "用户删除失败");
        }

        return ResultBuilder.getSuccess("用户删除成功");
    }

    @Override
    public ResultModel lockedUser(int userId) {
        if (userMapper.findUserById(userId) == null) {
            return ResultBuilder.getFailure(1, "用户不存在");
        }
        boolean isLock = userMapper.lockUser(userId);
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
        boolean isUnLock = userMapper.unLockUser(userId);
        if (isUnLock) {
            return ResultBuilder.getSuccess("用户解锁成功");
        }
        return ResultBuilder.getFailure(2, "用户解锁失败");
    }

}
