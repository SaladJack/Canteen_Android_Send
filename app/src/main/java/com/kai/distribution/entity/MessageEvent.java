package com.kai.distribution.entity;

/**
 * Created by tusm on 16/7/21.
 */
public class MessageEvent {
    int intMsg;
    String strMsg;

    public int getIntMsg() {
        return intMsg;
    }

    public void setIntMsg(int intMsg) {
        this.intMsg = intMsg;
    }

    public String getStrMsg() {
        return strMsg;
    }

    public void setStrMsg(String strMsg) {
        this.strMsg = strMsg;
    }

    public MessageEvent(int intMsg) {
        this.intMsg = intMsg;
    }

    public MessageEvent(String strMsg) {
        this.strMsg = strMsg;
    }
}
