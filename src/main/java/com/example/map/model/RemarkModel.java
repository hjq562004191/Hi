package com.example.map.model;

import java.util.Date;
import java.util.List;

public class RemarkModel {

    private int id;
    private String content;
    private Date createAt;
    private UserModel createBy;
    private Integer totalReplay;
    private int clickCount;
    private boolean isClick;
    private List<ReplayModel> replays;

    public RemarkModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public UserModel getCreateBy() {
        return createBy;
    }

    public void setCreateBy(UserModel createBy) {
        this.createBy = createBy;
    }

    public Integer getTotalReplay() {
        return totalReplay;
    }

    public void setTotalReplay(Integer totalReplay) {
        this.totalReplay = totalReplay;
    }

    public List<ReplayModel> getReplays() {
        return replays;
    }

    public void setReplays(List<ReplayModel> replays) {
        this.replays = replays;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
