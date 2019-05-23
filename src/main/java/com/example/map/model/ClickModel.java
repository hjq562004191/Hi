package com.example.map.model;

import java.io.Serializable;

public class ClickModel implements Serializable{
    private int id;
    private int userId;
    private int type;
    private int infoOrRemark;
    public ClickModel() {
    }

    public ClickModel(int userId, int type, int infoOrRemark) {
        this.userId = userId;
        this.type = type;
        this.infoOrRemark = infoOrRemark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getInfoOrRemark() {
        return infoOrRemark;
    }

    public void setInfoOrRemark(int infoOrRemark) {
        this.infoOrRemark = infoOrRemark;
    }
}
