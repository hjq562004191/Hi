package com.example.map.model;

import java.io.Serializable;
import java.util.Date;

public class InformationModel implements Serializable{
    private int id;
    private int pointId;
    private int type;
    private int userId;
    private int audioSecond;
    private int audioMinutes;
    private String username;
    private Object content;
    private int remarkCount;
    private int clickCount;
    private Date createAt;
    private boolean isClick;

    public int getAudioSecond() {
        return audioSecond;
    }

    public void setAudioSecond(int audioSecond) {
        this.audioSecond = audioSecond;
    }

    public int getAudioMinutes() {
        return audioMinutes;
    }

    public void setAudioMinutes(int audioMinutes) {
        this.audioMinutes = audioMinutes;
    }

    public InformationModel() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getRemarkCount() {
        return remarkCount;
    }

    public void setRemarkCount(int remarkCount) {
        this.remarkCount = remarkCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public boolean getIsClick() {
        return isClick;
    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }
}
