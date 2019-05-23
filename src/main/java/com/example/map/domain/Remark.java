package com.example.map.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户评论
 */
public class Remark implements Serializable {
    private Integer id;

    @NotNull(message = "infoId不能为空")
    private Integer infoId;
    @NotEmpty(message = "内容不能为空")
    private String content;
    private Date createAt;
    private Integer createBy;
    private Integer clickCount;

    public Remark() {
    }

    public Remark(Integer infoId, String content, Date createAt, Integer createBy, Integer clickCount) {
        this.infoId = infoId;
        this.content = content;
        this.createAt = createAt;
        this.createBy = createBy;
        this.clickCount = clickCount;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
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


    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}
