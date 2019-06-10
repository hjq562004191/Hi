package com.example.map.service;

import com.example.map.domain.Remark;
import com.example.map.model.ResultModel;

public interface RemarkService {
    ResultModel remarked(Remark remark);
    ResultModel getRemarks(int infoId, Integer userId, int pageNo, int pageSize);
    boolean save(Remark remark);
}
