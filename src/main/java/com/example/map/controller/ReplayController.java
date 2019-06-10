package com.example.map.controller;

import com.example.map.domain.Replay;
import com.example.map.mapper.UserMapper;
import com.example.map.model.ReplayModel;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Qiang
 */
@RestController
public class ReplayController {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private ReplayService replayService;

    @PostMapping("/replay/{commId}")
    public ResultModel addReplay(@PathVariable Integer commId, Integer toId,String content,
                                 HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        Replay replay = new Replay();
        replay.setContent(content);
        replay.setToId(toId);
        replay.setFromId(userId);
        replay.setCommId(commId);
        replay.setCreateAt(new Date());
        replay.setClick(0);
        if (replayService.save(replay)) {
            return ResultBuilder.getSuccess("回复成功");
        } else {
            return ResultBuilder.getFailure(1,"回复失败");
        }
    }

    @GetMapping("/none/replay")
    public ResultModel getReplays(int commId, int pageNo, int pageSize,HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        List<ReplayModel> replayModels = replayService.getByCommId(commId, pageNo, pageSize, userId);
        if (replayModels.size() == 0) {
            return ResultBuilder.getFailure(1, "无回复");
        } else {
            return ResultBuilder.getSuccess(replayModels);
        }
    }

}
