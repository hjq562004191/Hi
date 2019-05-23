package com.example.map.controller;

import com.example.map.model.ResultModel;
import com.example.map.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ClickController {
    @Autowired
    ClickService clickService;
    @RequestMapping(value = "/click", method = POST)
    public ResultModel click(int type, int infoOrRemarkId, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        return clickService.click(userId, type, infoOrRemarkId);
    }

    @RequestMapping(value = "/unclick", method = POST)
    public ResultModel unclick(int type, int infoOrRemarkId, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        return clickService.unclick(userId, type, infoOrRemarkId);
    }

//    @RequestMapping(value = "/getClicks", method = POST)
//    public ResultModel getClicks(int type, int infoOrRemarkId) {
//
//    }
}
