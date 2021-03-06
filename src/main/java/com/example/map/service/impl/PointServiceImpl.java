package com.example.map.service.impl;


import com.example.map.domain.Point;
import com.example.map.mapper.ItemsMapper;
import com.example.map.mapper.PointMapper;
import com.example.map.model.ItemsModel;
import com.example.map.model.PointAndItems;
import com.example.map.model.ResultBuilder;
import com.example.map.model.ResultModel;
import com.example.map.service.PointService;
import com.example.map.utils.GeoHash;
import com.example.map.utils.MapUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PointServiceImpl implements PointService {

    Logger logger = Logger.getLogger(PointServiceImpl.class);
    @Resource
    PointMapper pointMapper;

    @Resource
    ItemsMapper itemsMapper;

    @Override
    public ResultModel addPoint(String name, double longitude, double latitude, String geohash ,int id) {
        StringBuilder stringBuilder = new StringBuilder(geohash);
        if (pointMapper.findPoints(stringBuilder.substring(0,7)+ "%").size() == 0) {
            Point point = new Point();
            point.setName(name);
            point.setLongitude(longitude);
            point.setLatitude(latitude);
            point.setGeohash(geohash);
            point.setCreateAt(new Date());
            point.setCreateBy(id);
            if (pointMapper.savePoint(point) != 0) {
                logger.info("添加成功");
                point = pointMapper.findPointByXY(point.getLongitude(), point.getLatitude());
                ItemsModel itemsModel = new ItemsModel(point.getId(), 0, 0,0 ,0);
                if (itemsMapper.saveItems(itemsModel) == 0) {
                    logger.info("添加失败");
                    return ResultBuilder.getFailure(1, "添加失败");
                }
                return ResultBuilder.getSuccess(point.getId(),"添加成功");
            } else {
                logger.info("添加失败");
                return ResultBuilder.getFailure(1, "添加失败");
            }
        } else {
            logger.info("该点已存在");
            // 获取该点的信息
            Point point = pointMapper.findPointByXY(longitude, latitude);
            System.out.println(point);
            return ResultBuilder.getFailure(2, point,"该点已存在");
        }
    }

//    @Override
//    public ResultModel getPoints(Double longitude, Double latitude, Integer range) {
//        double dlon = MapUtil.dlon(range, latitude);
//        double dlat = MapUtil.dlat(range, longitude);
//        System.out.println(dlat + " " + dlon);
//        double x1 = longitude + dlon;
//        double x2 = longitude - dlon;
//        double y1 = latitude + dlat;
//        double y2 = latitude - dlat;
//        List<Point> points = pointMapper.findPoints(x1, x2, y1, y2);
//        List<PointAndItems> pointAndItems = new ArrayList();
//        for (Point point : points) {
//            ItemsModel itemsModel = itemsMapper.findItemsByPointId(point.getId());
//            pointAndItems.add(new PointAndItems(point, itemsModel));
//        }
//        if (points.size() == 0) {
//            return ResultBuilder.getFailure(1, "无点");
//        } else {
//            return ResultBuilder.getSuccess(pointAndItems);
//        }
//    }

    @Override
    public ResultModel getPoints(String geohash,Integer geolen) {
        StringBuilder sb = new StringBuilder(geohash);
        List<Point> points = pointMapper.findPoints(sb.substring(0,geolen) + "%");
        List<PointAndItems> pointAndItems = new ArrayList();

        if (points.size() == 0) {
            return ResultBuilder.getFailure(1, "无点");
        } else {
            for (Point point : points) {
                ItemsModel itemsModel = itemsMapper.findItemsByPointId(point.getId());
                pointAndItems.add(new PointAndItems(point, itemsModel));
            }
            return ResultBuilder.getSuccess(pointAndItems);
        }
    }

    @Override
    public ResultModel getItems(int pointId) {
        ItemsModel itemsModel = itemsMapper.findItemsByPointId(pointId);
        if (itemsModel != null) {
            return ResultBuilder.getSuccess(itemsModel);
        } else {
            return ResultBuilder.getFailure(1, "该点不存在");
        }
    }

    @Override
    public ResultModel lockPoint(int pointId) {
        boolean isExist = pointMapper.isExistPoint(pointId);
        if (!isExist) {
            return ResultBuilder.getFailure(1, "该点不存在");
        }
        if (pointMapper.isLockedPoint(pointId) == 1) {
            return ResultBuilder.getFailure(2, "该点已经被锁定");
        }
        boolean lockPoint = pointMapper.lockPoint(pointId);
        if (lockPoint) {
            return ResultBuilder.getSuccess("该点锁定成功");
        }
        return ResultBuilder.getFailure(3, "该点锁定失败");
    }



    @Override
    public ResultModel unLockPoint(int pointId) {
        boolean isExist = pointMapper.isExistPoint(pointId);
        if (!isExist) {
            return ResultBuilder.getFailure(1, "该点不存在");
        }
        if (pointMapper.isLockedPoint(pointId) == 0) {
            return ResultBuilder.getFailure(2, "该点没有被锁定");
        }
        boolean unlockPoint = pointMapper.unlockPoint(pointId);
        if (unlockPoint) {
            return ResultBuilder.getSuccess("该点解锁成功");
        }
        return ResultBuilder.getFailure(3, "该点解锁失败");
    }

    @Override
    public Point getPointById(int pointId) {
        return pointMapper.findPointById(pointId);
    }
}
