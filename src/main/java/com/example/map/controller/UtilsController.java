package com.example.map.controller;


import com.example.map.model.ResultModel;
import com.example.map.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
public class UtilsController {
    @Autowired
    UtilsService utilsService;
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
