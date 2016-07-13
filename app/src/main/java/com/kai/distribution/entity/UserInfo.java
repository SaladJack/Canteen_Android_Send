package com.kai.distribution.entity;

/**
 * Created by Dell on 2016/6/23.
 */
public class UserInfo {

    private String result;
    private String workerName;
    private String photo;
    private String code;
    private String account;
    private int workerId;
    private String phoneNumber;

    @Override
    public String toString() {
        return "UserInfo{" +
                "account='" + account + '\'' +
                ", result='" + result + '\'' +
                ", workerName='" + workerName + '\'' +
                ", photo='" + photo + '\'' +
                ", code='" + code + '\'' +
                ", workerId=" + workerId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {

        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
