package com.kai.distribution.entity;

/**
 * Created by tusm on 16/7/21.
 */
public class MessageEvent {
    int intMsg;
    String area;

    public int getIntMsg() {
        return intMsg;
    }

    public void setIntMsg(int intMsg) {
        this.intMsg = intMsg;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public MessageEvent(int intMsg) {
        this.intMsg = intMsg;
    }

    public MessageEvent(String strMsg) {
        this.area = strMsg;
    }



}
