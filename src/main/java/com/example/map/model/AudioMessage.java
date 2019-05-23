package com.example.map.model;

import java.io.Serializable;

/**
 * @author Qiang
 */
public class AudioMessage implements Serializable {
    private String url;
    private int audioSecond;
    private int audioMinutes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
}
