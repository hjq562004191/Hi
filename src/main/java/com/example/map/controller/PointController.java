package com.example.map.controller;

import com.auth0.jwt.interfaces.Claim;
import com.example.map.domain.Point;
import com.example.map.model.ResultModel;
import com.example.map.service.PointService;
import com.example.map.service.UserService;
import com.example.map.utils.DoubleUtil;
import com.example.map.utils.GeoHash;
import com.example.map.utils.JWTUtils;
import com.example.map.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PointController {
    @Autowired
    PointService pointService;
    @Autowired
    UserService userService;
    /**
     *
     * @param name 点的名字
     * @param longitude 经度
     * @param latitude  纬度
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/point/addPoint", method = POST)
        public ResultModel addPoint(String name, double longitude, double latitude,
                                    HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String token = request.getHeader("token");
        Map<String, Claim> map = null;
        try {
            map = JWTUtils.verifyToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/utils/logonExpires").forward(request, response);
        }
        int id = map.get("id").asInt();
        longitude = DoubleUtil.accuracy(longitude, 5);
        latitude = DoubleUtil.accuracy(latitude, 5);
        GeoHash GH = new GeoHash(latitude,longitude);
        String geohash = GH.getGeoHashBase32();
        return pointService.addPoint(name, longitude, latitude, geohash ,id);
    }

    @RequestMapping("/none/getPoints")
    public ResultModel getPoints(Double longitude, Double latitude, Integer range) {
        Integer geolen = null;
        if (range < 20){  // 七位代表范围在19米左右
            geolen = 7;
        }else if (range < 80){   //  范围76米
            geolen = 6;
        }else if (range < 610){   // 范围610米
            geolen = 5;
        }else if (range < 2400){  // 范围2400米
            geolen = 4;
        }
        return pointService.getPoints(new GeoHash(latitude,longitude).getGeoHashBase32(),geolen);
    }

    @RequestMapping("/none/getItems/{pointId}")
    public ResultModel getItems(@PathVariable int pointId) {
        return pointService.getItems(pointId);
    }

    @RequestMapping("/admin/lockpoint/{pointId:\\d+}")
    public ResultModel lockPoint(@PathVariable int pointId) {
        return pointService.lockPoint(pointId);
    }

    @RequestMapping("/admin/unlockpoint/{pointId:\\d+}")
    public ResultModel unLockPoint(@PathVariable int pointId) {
        return pointService.unLockPoint(pointId);
    }


    /**
     * 返回点二维码的url,保存在服务器上
     * @param pointId
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws WriterException
     */
    @SuppressWarnings("all")
    @RequestMapping("/user/sharePoint/{pointId:\\d+}")
    public ResultModel PointShare(@PathVariable int pointId,
                                  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, WriterException {
        String token = request.getHeader("token");
        Map<String, Claim> map = null;
        try {
            map = JWTUtils.verifyToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/utils/logonExpires").forward(request, response);
        }
        int userId = map.get("id").asInt();
        String logoUrl = userService.getUserIconById(userId);
        /**
         * 获取分享者的Id
         * 找到id的头像放到二维码中当作logo
         */
        String content = "http://39.106.39.48:8080/user/ReadShareQRcode/" + pointId;
        return QRCodeUtils.QREncode(content,logoUrl);
    }

    @RequestMapping("/user/ReadShareQRcode/{pointId:\\d+}")
    public Point ReadShareQRcode(@PathVariable int pointId){
        return pointService.getPointById(pointId);
    }
}
