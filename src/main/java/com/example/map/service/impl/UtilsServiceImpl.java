package com.example.map.service.impl;

import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.UtilsService;
import org.springframework.stereotype.Service;

@Service
public class UtilsServiceImpl implements UtilsService {
    @Override
    public ResultModel notLoginIn() {
        return ResultBuilder.getFailure(-1, "未登录");
    }

    @Override
    public ResultModel logonExpires() {
        return ResultBuilder.getFailure(-2, "登录过期");
    }

    @Override
    public ResultModel loginException() {
        return ResultBuilder.getFailure(-3, "登录异常");
    }

    @Override
    public ResultModel fileNotAllow() {
        return ResultBuilder.getFailure(-1, "不支持的文件类型");
    }

    @Override
    public ResultModel adminNotLoginIn() {
        return ResultBuilder.getFailure(-1, "管理员未登录");
    }

    @Override
    public ResultModel noJurisdiction() {
        return ResultBuilder.getFailure(-4, "权限不足");
    }


}
