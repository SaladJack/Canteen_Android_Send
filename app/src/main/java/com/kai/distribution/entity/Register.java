package com.kai.distribution.entity;

/**
 * Created by tusm on 16/7/15.
 */
public class Register {


    public Register(String account, String name, String password, String recognizeCode) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.recognizeCode = recognizeCode;
    }

    private String account;
    private String name;
    private String password;
    private String recognizeCode;

    private static Register mInstance = new Register("account","name","password","recognizeCode");

    public static Register getInstance(){
        return mInstance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecognizeCode() {
        return recognizeCode;
    }

    public void setRecognizeCode(String recognizeCode) {
        this.recognizeCode = recognizeCode;
    }

    @Override
    public String toString() {
        return "Register{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", recognizeCode='" + recognizeCode + '\'' +
                '}';
    }


}
