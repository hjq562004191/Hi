package com.example.map.service.impl;

import com.example.map.domain.Remark;
import com.example.map.mapper.ClickMapper;
import com.example.map.mapper.InformationMapper;
import com.example.map.mapper.RemarkMapper;
import com.example.map.model.InformationModel;
import com.example.map.model.RemarkModel;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.RemarkService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Qiang
 */
@Service
public class RemarkServiceImpl implements RemarkService {
    @Resource
    RemarkMapper remarkMapper;
    @Resource
    ClickMapper clickMapper;
    @Resource
    InformationMapper informationMapper;
    @Override
    public ResultModel remarked(Remark remark) {
        if (saveRemark(remark)) {
            return ResultBuilder.getSuccess("评论成功");
        } else {
            return ResultBuilder.getFailure(1, "评论失败");
        }
    }


    @Override
    public ResultModel getRemarks(int infoId, Integer userId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<RemarkModel> remarkModels = remarkMapper.getRemarks(infoId);
        if (remarkModels.size() != 0) {
            if (userId != null) {
                for (RemarkModel remarkModel : remarkModels) {
                    boolean isClick = clickMapper.isExist(userId, 2, remarkModel.getId());
                    remarkModel.setClick(isClick);
                }
            }
            return ResultBuilder.getSuccess(remarkModels);
        } else {
            return ResultBuilder.getFailure(1, "无消息");
        }
    }

    @Transactional
    @Override
    public boolean save(Remark remark) {
        int saveStatus = remarkMapper.saveRemark(remark);
        if (saveStatus == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    public boolean saveRemark(Remark remark) {
        InformationModel informationModel = informationMapper.findById(remark.getInfoId());
        informationModel.setRemarkCount(informationModel.getRemarkCount() + 1);
        if (informationMapper.updaateClickOrRemark(informationModel) == 0) {
            return false;
        } else {
            if(remarkMapper.saveRemark(remark) == 0) {
                return false;
            }
        }
        return true;
    }
}
