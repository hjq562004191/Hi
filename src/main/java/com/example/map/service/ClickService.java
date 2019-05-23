package com.example.map.service;

import com.example.map.model.ClickModel;
import com.example.map.model.ResultModel;
import org.springframework.transaction.annotation.Transactional;

public interface ClickService {
    ResultModel click(int userId, int type, int infoOrRemark);

    ResultModel unclick(int userId, int type, int infoOrRemark);
    @Transactional
    boolean isClick(ClickModel clickModel);

    boolean deleteClick(ClickModel clickModel);
}
