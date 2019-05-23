package com.example.map.service;

import com.example.map.domain.Replay;
import com.example.map.model.ReplayModel;

import java.util.List;

public interface ReplayService {
    boolean save(Replay replay);
    List<ReplayModel> getByCommId(Integer commId, Integer pageNo, Integer pageSize, Integer userId);
}
