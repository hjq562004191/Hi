package com.example.map.service;

import com.example.map.model.ImageMessage;
import com.example.map.model.ResultModel;

/**
 * @author Qiang
 * @data 2019/5/22/17:59
 */
public interface FileUploadService {
        ResultModel addVideo(int pointId,int userId,String content);
        ResultModel addAudio(int pointId,int userId,String content);
        ResultModel addPhotos(Integer userId, int pointId, ImageMessage imageMessage);
}
