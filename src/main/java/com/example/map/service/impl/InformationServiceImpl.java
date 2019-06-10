package com.example.map.service.impl;

import com.example.map.domain.Information;
import com.example.map.mapper.*;
import com.example.map.model.*;

import com.example.map.service.InformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Qiang
 */
@Service
public class InformationServiceImpl implements InformationService {

    private final static int PHOTO = 1;
    private final static int AUDIO = 2;
    private final static int VIDEO = 3;


    @Resource
    InformationMapper informationMapper;
    @Resource
    ItemsMapper itemsMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ClickMapper clickMapper;
    @Resource
    InformationService informationService;
    @Resource
    PointMapper pointMapper;

    /**
     * 需要添加判断点是否存在
     */
    @Override
    public ResultModel addMessage(int pointId, String content, int userId) {
        // 判断需要添加的点是否存在
        if (!pointMapper.isExistPoint(pointId)) {
            ResultBuilder.getFailure(2, "点不存在");
        }
        CommMessage commMessage = new CommMessage();
        commMessage.setComm(content);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            content = objectMapper.writeValueAsString(commMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Information information = new Information(pointId, userId, 0, content, 0, 0, new Date());

        if (informationService.insertInformation(information)) {
            return ResultBuilder.getSuccess("添加成功");
        } else {
            return ResultBuilder.getFailure(1, "添加失败");
        }
    }

    @Override
    public ResultModel getMessages(Integer userId, int pointId, int type) {

        List<InformationModel> informations = informationMapper.findInformation(pointId, type);

        if (informations.size() != 0) {
            for (InformationModel informationModel : informations) {
                handlerInformation(informationModel, type);
            }
            if (userId != null) {
                informationService.checkClick(informations, userId, 1);
            }
            return ResultBuilder.getSuccess(informations);
        } else {
            return ResultBuilder.getFailure(1, "无消息");
        }
    }



    @Override
    public ResultModel addtouxiang(int userId, String url) {
        UserModel userModel = userMapper.findUserModelById(userId);
        if (userModel == null){
            return ResultBuilder.getFailure(-2,"没有此用户");
        }else {
            if (userMapper.updateImage(userId,url)){
                return ResultBuilder.getSuccess("头像上传成功");
            }else {
                return ResultBuilder.getFailure(-3,"头像上传失败");
            }
        }
    }

    @Override
    public ResultModel deleteInformation(int id,int userid) {
        InformationModel information = informationMapper.findById(id);
        if (information == null) {
            return ResultBuilder.getFailure(1, "信息不存在");
        }
        if (userid != information.getUserId()){
            //不是自己写的不能删除
            return ResultBuilder.getFailure(-1, "删除失败，您没有权限");
        }

        ItemsModel itemsModel = itemsMapper.findItemsByPointId(information.getPointId());
        switch (information.getType()) {
            case 0:
                itemsModel.setMesCount(itemsModel.getMesCount() - 1);
                break;
            case 1:
                itemsModel.setPhoCount(itemsModel.getPhoCount() - 1);
                break;
            case 2:
                itemsModel.setAudCount(itemsModel.getAudCount() - 1);
                break;
            case 3:
                itemsModel.setVidCount(itemsModel.getVidCount() - 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + information.getType());
        }
        itemsMapper.updateItems(itemsModel);
        informationMapper.delete(id);
        return ResultBuilder.getSuccess("删除成功");
    }

    @Override
    public ResultModel lockInformation(int infoId) {
        boolean isExist = informationMapper.isExist(infoId);
        if (!isExist) {
            return ResultBuilder.getFailure(1, "锁定的信息不存在");
        }
        if (informationMapper.isLockInformation(infoId) == 1) {
            return ResultBuilder.getFailure(2, "该信息已经被锁定");
        }
        boolean lockInfo = informationMapper.lockInformation(infoId);
        if (lockInfo) {
            return ResultBuilder.getSuccess("信息锁定成功");
        }
        return ResultBuilder.getFailure(3, "信息锁定失败");
    }

    @Override
    public ResultModel unLockInformation(int infoId) {
        boolean isExist = informationMapper.isExist(infoId);
        if (!isExist) {
            return ResultBuilder.getFailure(1, "锁定的信息不存在");
        }
        if (informationMapper.isLockInformation(infoId) == 0) {
            return ResultBuilder.getFailure(2, "该信息没有被锁定");
        }
        boolean lockInfo = informationMapper.unLockInformation(infoId);
        if (lockInfo) {
            return ResultBuilder.getSuccess("信息解锁成功");
        }
        return ResultBuilder.getFailure(3, "信息解锁失败");
    }

    @Override
    @Transactional
    public boolean insertInformation(Information information) {
        ItemsModel itemsModel = itemsMapper.findItemsByPointId(information.getPointId());
        switch (information.getType()) {
            case 0:
                itemsModel.setMesCount(itemsModel.getMesCount() + 1);
                break;
            case 1:
                itemsModel.setPhoCount(itemsModel.getPhoCount() + 1);
                break;
            case 2:
                itemsModel.setAudCount(itemsModel.getAudCount() + 1);
                break;
            case 3:
                itemsModel.setVidCount(itemsModel.getVidCount() + 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + information.getType());
        }
        if (itemsMapper.updateItems(itemsModel) == 0) {

            return false;
        }
        if (informationMapper.saveMessage(information) == 0) {
            return false;
        }
        return true;
    }


    @Override
    public void checkClick(List<InformationModel> informationModels,
                           int userId, int type) {
        for (InformationModel informationModel : informationModels) {
            if (clickMapper.isExist(userId, type, informationModel.getId())) {
                informationModel.setIsClick(true);
            } else {
                informationModel.setIsClick(false);
            }
        }

    }

    private void handlerInformation(InformationModel informationModel, Integer type) {
        String content = (String) informationModel.getContent();
        int userId = informationModel.getUserId();
        informationModel.setUsername(userMapper.findUsernameById(userId));
        ObjectMapper objectMapper = new ObjectMapper();
        CommMessage commMessage;
        AudioMessage audioMessage ;
        ImageMessage imageMessage ;
        VideoMessage videoMessage ;
        try {
            if (type == AUDIO) {
                audioMessage = objectMapper.readValue(content, AudioMessage.class);
                informationModel.setContent(audioMessage);
            }else if (type == VIDEO){
                videoMessage = objectMapper.readValue(content, VideoMessage.class);
                informationModel.setContent(videoMessage);
            }else if (type == PHOTO){
                imageMessage = objectMapper.readValue(content, ImageMessage.class);
                informationModel.setContent(imageMessage);
            }else {
                commMessage = objectMapper.readValue(content, CommMessage.class);
                informationModel.setContent(commMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
