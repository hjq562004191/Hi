package com.example.map.service;

import com.example.map.model.ResultModel;

public interface UtilsService {

    ResultModel notLoginIn();

    ResultModel logonExpires();
    ResultModel loginException();

    ResultModel fileNotAllow();

    ResultModel adminNotLoginIn();

    ResultModel noJurisdiction();
}
