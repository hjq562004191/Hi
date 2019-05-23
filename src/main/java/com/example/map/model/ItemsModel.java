package com.example.map.model;

import java.io.Serializable;

public class ItemsModel implements Serializable{
    private int pointId;
    private int mesCount;
    private int phoCount;
    private int audCount;
    private int vidCount;

    public ItemsModel() {
    }

    public ItemsModel(int pointId, int mesCount, int phoCount, int audCount, int vidCount) {
        this.pointId = pointId;
        this.mesCount = mesCount;
        this.phoCount = phoCount;
        this.audCount = audCount;
        this.vidCount = vidCount;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
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
