package com.example.map.controller;

import com.example.map.model.ResultModel;
import com.example.map.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Qiang
 */
@RestController
public class InformationController {

    @Autowired
    private UserController userController;

    @Autowired
    InformationService informationService;

    @RequestMapping(value = "/addMessage/{pointId}", method = POST)
    public ResultModel addMessage(@PathVariable int pointId, String content, HttpServletRequest request) {
        int id = (Integer) request.getAttribute("id");
        System.out.println(id);
        return informationService.addMessage(pointId, content, id);
    }

    @RequestMapping("/none/getMessage/{pointId}")
    public ResultModel getMessage(@PathVariable int pointId, int type,
                                  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = userController.getUserIdFromToken(request, response);
        return informationService.getMessages(userId, pointId, type);
    }

    @RequestMapping("/deleteinformation/{id:\\d+}")
    public ResultModel deleteInformation(@PathVariable int id,
                                    HttpServletRequest request) {
        int userid = (int) request.getAttribute("id");
        return informationService.deleteInformation(id,userid);
    }

    @RequestMapping("/admin/lockinfo/{infoId:\\d+}")
    public ResultModel lockInfo(@PathVariable int infoId) {
        return informationService.lockInformation(infoId);
    }

    @RequestMapping("/admin/unlockinfo/{infoId:\\d+}")
    public ResultModel unlockInfo(@PathVariable int infoId) {
        return informationService.unLockInformation(infoId);
    }
}
