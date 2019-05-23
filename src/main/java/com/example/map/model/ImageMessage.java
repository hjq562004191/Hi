package com.example.map.model;

import java.io.Serializable;

/**
 * @author Qiang
 */
public class ImageMessage implements Serializable {

    private String title;
    private String[] urls;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrls(String[] url) {
        this.urls = url;
    }

    public String[] getUrls() {
        return urls;
    }
}
