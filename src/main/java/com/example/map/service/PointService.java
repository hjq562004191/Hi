package com.example.map.service;

import com.example.map.model.ResultModel;

public interface PointService {
    ResultModel addPoint(String name, double longitude, double latitude, int id);

    ResultModel getPoints(double longitude, double latitude, int range);

    ResultModel getItems(int pointId);

    ResultModel lockPoint(int pointId);

    ResultModel unLockPoint(int pointId);
}
