package com.example.map.service.impl;

import com.example.map.domain.Replay;
import com.example.map.mapper.ClickMapper;
import com.example.map.mapper.ReplayMapper;
import com.example.map.model.ReplayModel;
import com.example.map.service.ReplayService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplayServiceImpl implements ReplayService {

    @Resource
    private ReplayMapper replayMapper;

    @Resource
    private ClickMapper clickMapper;

    @Transactional
    @Override
    public boolean save(Replay replay) {
        int status  = replayMapper.save(replay);
        if (status == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<ReplayModel> getByCommId(Integer commId, Integer pageNo, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ReplayModel> replayModels = replayMapper.selectAllByCommId(commId);
        if (userId != null) {
            for (ReplayModel replayModel : replayModels) {
                if (clickMapper.isExist(userId, 3, replayModel.getId())) {
                    replayModel.setIsClick(true);
                } else {
                    replayModel.setIsClick(false);
                }
            }
        }
        return replayModels;
    }
}
