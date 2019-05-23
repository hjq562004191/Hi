package com.example.map.controller;

import com.example.map.domain.Remark;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
public class RemarkController {
    @Autowired
    RemarkService remarkService;

    @Autowired
    private UserController userController;

    @PostMapping(value = "/remark/{infoId}")
    public ResultModel remark(@PathVariable Integer infoId, @RequestParam String content, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        Remark remark = new Remark();
        remark.setInfoId(infoId);
        remark.setCreateBy(userId);
        remark.setContent(content);
        System.out.println(content);
        remark.setCreateAt(new Date());
        remark.setClickCount(0);
        if (remarkService.save(remark)) {
            return ResultBuilder.getSuccess("评论成功");
        } else {
            return ResultBuilder.getFailure(1, "评论失败");
        }
    }
    @RequestMapping("/none/getRemarks")
    public ResultModel resultModel(int infoId, HttpServletRequest request,
                                   HttpServletResponse response, int pageNo, int pageSize) throws ServletException, IOException {
        Integer userId = userController.getUserIdFromToken(request, response);
        return remarkService.getRemarks(infoId, userId, pageNo, pageSize);
    }
}
