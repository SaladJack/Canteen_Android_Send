package com.kai.distribution.entity;


import com.alibaba.fastjson.JSONArray;

/**
 * Created by tusm on 16/7/18.
 */
public class Distributed {


    /**
     *  groupId : 箱子Id(int)
     *  sendMoney : 配送费(long)
     *  getAccount : 取餐号(string)
     *  statu : 订单状态(int)
     *  canteen : 餐厅名(string)
     *  area : 校区代号(int)               **********
     *  goods : 餐品和对应的数量(JSONArray)
     *  account : 订单号(string)
     *  workerName : 配送员名字(string)
     *  workerPhone : 配送员手机号(string)
     *  payMoney : 实付款(long)
     *  stuName : 学生名字(string)         **********
     *  phoneNumber : 学生手机号(string)
     *  time : 下单时间毫秒数(long)
     *  address : 配送地址(string)         **********
     *  pay : 支付方式代号(int) （校园卡0，微信2，支付宝1）
     *  sendTimeBegin : 送达开始时间毫秒数(long)
     *  sendTimeEnd  : 送达结束时间毫秒数(long)
     *  realTime : 实际送达时间毫秒数(long)
     *  couLimitMoney : 优惠券使用限制金额(long)(单位:分)(无优惠券时返回0)
     *  couDecreaseMoney : 优惠券减少金额(long)(单位:分) (无优惠券时返回0)
     *  actLimitMoney : 优惠活动限制金额(long)(单位:分) (无优惠活动时返回0)
     *  actDecreaseMoney : 优惠活动减少金额(long)(单位:分) (无优惠活动时返回0)
     *  orderId : 餐品Id(int)
     *  goodNumber : 餐品数量(int)
     */

    private int groupId;
    private long sendMoney;
    private String getAccount;
    private int statu;
    private String canteen;
    private int area;
    private JSONArray goods;//注意：这里是fastjson的JSONArray 不能更改！！！
    private String account;
    private String workerName;
    private String workerPhone;
    private String payMoney;
    private String stuName;
    private String phoneNumber;
    private long time;
    private String address;
    private int pay;
    private long sendTimeBegin;
    private long sendTimeEnd;
    private long realTime;
    private long couLimitMoney;
    private long couDecreaseMoney;
    private long actLimitMoney;
    private long actDecreaseMoney;
    private int orderId;
    private int goodNumber;

    public Distributed(){

    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public long getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(long sendMoney) {
        this.sendMoney = sendMoney;
    }

    public String getGetAccount() {
        return getAccount;
    }

    public void setGetAccount(String getAccount) {
        this.getAccount = getAccount;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setCanteen(String canteen) {
        this.canteen = canteen;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public JSONArray getGoods() {
        return goods;
    }

    public void setGoods(JSONArray goods) {
        this.goods = goods;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public long getSendTimeBegin() {
        return sendTimeBegin;
    }

    public void setSendTimeBegin(long sendTimeBegin) {
        this.sendTimeBegin = sendTimeBegin;
    }

    public long getSendTimeEnd() {
        return sendTimeEnd;
    }

    public void setSendTimeEnd(long sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd;
    }

    public long getRealTime() {
        return realTime;
    }

    public void setRealTime(long realTime) {
        this.realTime = realTime;
    }

    public long getCouLimitMoney() {
        return couLimitMoney;
    }

    public void setCouLimitMoney(long couLimitMoney) {
        this.couLimitMoney = couLimitMoney;
    }

    public long getCouDecreaseMoney() {
        return couDecreaseMoney;
    }

    public void setCouDecreaseMoney(long couDecreaseMoney) {
        this.couDecreaseMoney = couDecreaseMoney;
    }

    public long getActLimitMoney() {
        return actLimitMoney;
    }

    public void setActLimitMoney(long actLimitMoney) {
        this.actLimitMoney = actLimitMoney;
    }

    public long getActDecreaseMoney() {
        return actDecreaseMoney;
    }

    public void setActDecreaseMoney(long actDecreaseMoney) {
        this.actDecreaseMoney = actDecreaseMoney;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(int goodNumber) {
        this.goodNumber = goodNumber;
    }
}
