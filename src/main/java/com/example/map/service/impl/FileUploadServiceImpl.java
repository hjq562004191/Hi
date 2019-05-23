package com.example.map.service.impl;

import com.example.map.domain.Information;
import com.example.map.model.ImageMessage;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.FileUploadService;
import com.example.map.service.InformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Qiang
 * @data 2019/5/22/18:01
 */
@Service
@Component
public class FileUploadServiceImpl implements FileUploadService {

    private final static int AUDIO = 2;
    private final static int VIDEO = 3;

    @Resource
    InformationService informationService;

    @Override
    public ResultModel addVideo(int pointId,int userId, String content) {
        Information information = new Information(pointId, userId, VIDEO, content, 0, 0, new Date());
        if (informationService.insertInformation(information)) {
            return ResultBuilder.getSuccess("文件上传成功");
        } else {
            return ResultBuilder.getFailure(3, "文件上传失败");
        }
    }

    @Override
    public ResultModel addAudio(int pointId,int userId, String content) {
        Information information = new Information(pointId, userId, AUDIO, content, 0, 0, new Date());
        if (informationService.insertInformation(information)) {
            return ResultBuilder.getSuccess("文件上传成功");
        } else {
            return ResultBuilder.getFailure(3, "文件上传失败");
        }
    }

    @Override
    public ResultModel addPhotos(Integer userId, int pointId, ImageMessage imageMessage) {
        Information information = new Information();
        information.setPointId(pointId);
        information.setUserId(userId);
        information.setType(1);
        information.setCreateAt(new Date());
        information.setClickCount(0);
        information.setRemarkCount(0);

        String content = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            content = objectMapper.writeValueAsString(imageMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        information.setContent(content);
        if (informationService.insertInformation(information)) {
            return ResultBuilder.getSuccess(imageMessage.getUrls(),"文件上传成功");
        } else {
            return ResultBuilder.getFailure(3, "文件上传失败");
        }
    }

}
