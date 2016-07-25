package com.kai.distribution.entity;

import java.io.Serializable;

/**
 * Created by tusm on 16/7/25.
 */
public class Message implements Serializable {
    private String content;
    private long time;
    private int messageId;

    public Message(){
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
