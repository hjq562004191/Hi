package com.example.map.domain;

import java.util.Date;

/**
 * 回复
 */
public class Replay {

    private Integer id; // 回复的id

    private Integer commId; // 评论的id

    private Integer fromId; // 回复的用户的Id

    private Integer toId; // 目标用户的Id

    private String content;    // 评论的内容

    private Integer click;      // 点赞数

    private Date createAt;      // 创建的时间


    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
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
}
