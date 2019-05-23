package com.example.map.model;

import java.util.Date;

/**
 * 回复
 */
public class ReplayModel {

    private Integer id; // 回复的id

    private Integer commId; // 评论的id

    private UserModel fromUser; // 回复的用户

    private UserModel toUser; // 目标用户

    private String content;    // 评论的内容

    private Date createAt;

    private boolean isClick;

    private Integer click;      // 点赞数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommId() {
        return commId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public UserModel getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserModel fromUser) {
        this.fromUser = fromUser;
    }

    public UserModel getToUser() {
        return toUser;
    }

    public void setToUser(UserModel toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public void setIsClick(Boolean click) {
        isClick = click;
    }

    public Boolean getIsClick() {
        return isClick;
    }
}
