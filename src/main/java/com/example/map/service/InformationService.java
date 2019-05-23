package com.example.map.service;

import com.example.map.domain.Information;
import com.example.map.model.ImageMessage;
import com.example.map.model.InformationModel;
import com.example.map.model.ResultModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Qiang
 */
public interface InformationService {

    ResultModel addMessage(int pointId, String content, int uerId);

    ResultModel getMessages(Integer userId, int pointId, int type);
    void checkClick(List<InformationModel> informationModels,
                    int userId, int type);
    @Transactional
    boolean insertInformation(Information information);

//    ResultModel addMangPhotosMessage(Integer userId, int pointId, ImageMessage imageMessage);

    ResultModel addtouxiang(int userId, String url);

    ResultModel lockInformation(int infoId);

    ResultModel unLockInformation(int infoId);

    ResultModel deleteInformation(int id, int userid);
}
