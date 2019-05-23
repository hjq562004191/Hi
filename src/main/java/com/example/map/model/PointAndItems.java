package com.example.map.model;

import com.example.map.domain.Point;

import java.util.Date;

public class PointAndItems {
    private int id;
    private String name;
    private double longitude;
    private double latitude;
    private Date createAt;
    private int createBy;
    private int mesCount;
    private int phoCount;
    private int audCount;
    private int vidCount;

    public PointAndItems(Point point, ItemsModel itemsModel) {
        this.id = point.getId();
        this.name = point.getName();
        this.longitude = point.getLongitude();
        this.latitude = point.getLatitude();
        this.createAt = point.getCreateAt();
        this.createBy = point.getCreateBy();
        this.mesCount = itemsModel.getMesCount();
        this.phoCount = itemsModel.getPhoCount();
        this.audCount = itemsModel.getAudCount();
        this.vidCount = itemsModel.getVidCount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public int getMesCount() {
        return mesCount;
    }

    public void setMesCount(int mesCount) {
        this.mesCount = mesCount;
    }

    public int getPhoCount() {
        return phoCount;
    }

    public void setPhoCount(int phoCount) {
        this.phoCount = phoCount;
    }

    public int getAudCount() {
        return audCount;
    }

    public void setAudCount(int audCount) {
        this.audCount = audCount;
    }

    public int getVidCount() {
        return vidCount;
    }

    public void setVidCount(int vidCount) {
        this.vidCount = vidCount;
    }
}
