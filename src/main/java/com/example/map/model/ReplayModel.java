package com.example.map.model;


/**
 * 回复
 * @author Qiang
 */
public class ReplayModel {
    /**
     * 回复的id
      */

    private Integer id;
    /**
     *评论的id
     */
    private Integer commId;
    /**
     *回复的用户
     */
    private UserModel fromUser;
    /**
     *目标用户
     */
    private UserModel toUser;
    /**
     *评论的内容
     */
    private String content;

    private String  createAt;

    private boolean isClick;

    /**
     *点赞数
     */
    private Integer click;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommId() {
        return commId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
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
