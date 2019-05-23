package com.example.map.utils;

import static java.lang.Math.PI;

public class MapUtil {
    // 赤道半径
    private static double EARTH_RADIUS = 6378.393;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private static Double degree = (24901 * 1609) / 360.0;

    /**
     * 计算两个经纬度之间的距离
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    public static double dlon(int range, double latitude) {
        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * range;
        return radiusLng;
    }

    public static double dlat(int range, double longtitude) {
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * range;
        return radiusLat;
    }
}