package com.example.map.controller;


import com.example.map.model.ResultBuilder;
import com.example.map.utils.PhoneUtil;
import com.example.map.model.ResultModel;
import com.example.map.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Qiang
 */
@RestController
@RequestMapping("/utils")
public class UtilsController {
    @Autowired
    UtilsService utilsService;


    @RequestMapping(value = "/getphonecode", method = POST)
    public ResultModel getphonecode(String phonenumber) {
        String code = null;
        code = PhoneUtil.getVerificationCode(phonenumber);
        if (code == null){
            return ResultBuilder.getFailure(-1, "验证码发送失败!");
        }
        return ResultBuilder.getSuccess(code,"获取成功!");
    }


    @RequestMapping(value = "/judgephone" , method = POST)
    public  ResultModel phoneNumberJudge(String code,String phonenumber){
        if (PhoneUtil.judgeCodeIsTrue(code,phonenumber)){
            return ResultBuilder.getSuccess("验证成功!");
        }
        return ResultBuilder.getFailure(-1,"验证失败!");
    }


    @RequestMapping("/notLoginIn")
    public ResultModel notLoginIn() {
        return utilsService.notLoginIn();
    }

    @RequestMapping("/adminNotLoginIn")
    public ResultModel adminNotLoginIn() {
        return utilsService.adminNotLoginIn();
    }

    @RequestMapping("/logonExpires")
    public ResultModel logonExpires() {
        return utilsService.logonExpires();
    }

    @RequestMapping("/loginException")
    public ResultModel loginException() {
        return utilsService.loginException();
    }

    @RequestMapping("/fileNotAllow")
    public ResultModel fileNotNull() {
        return utilsService.fileNotAllow();
    }

    @RequestMapping("/noJurisdiction")
    public ResultModel noJurisdiction() {
        return utilsService.noJurisdiction();
    }
}
