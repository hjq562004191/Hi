package com.example.map.service;

import com.example.map.domain.Point;
import com.example.map.model.ResultModel;

public interface PointService {
    ResultModel addPoint(String name, double longitude, double latitude,String geohash, int id);

//    ResultModel getPoints(Double longitude, Double latitude, Integer range);

    ResultModel getPoints(String geohash,Integer geolen);

    ResultModel getItems(int pointId);

    ResultModel lockPoint(int pointId);

    ResultModel unLockPoint(int pointId);

    Point getPointById(int pointId);
}
