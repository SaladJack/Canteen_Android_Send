package com.kai.distribution.app;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * author:hh
 * Created by Dell on 2016/6/21.
 * 此类用来存储常量，URL，Key等
 */
public class Constants {

    //SharePreference的存储路径
    public static final String SHARED_PATH="re_share";


    public static  class URL{
        public static final String BASE_URL="http://milk345.imwork.net:13607/";
        //登录
        public static final String LOGIN_URL=BASE_URL+"Canteen/worker/login";
        //退出
        public static final String LOGOUT_URL=BASE_URL+"Canteen/worker/logout";
        //认证
        public static final String REGISTER_URL=BASE_URL+"Canteen/worker/setPassword";
        //系统消息
        public static final String SYS_MESSAGE_URL=BASE_URL+"Canteen/worker/showMessages";
        //已送达订单
        public static final String DISTRIBUTED_URL = BASE_URL+"Canteen/worker/showSendedOrders";
        //绑定手机
        public static final String BIND_PHONE = BASE_URL + "Canteen/worker/bindPhone";
        //发送短信（获得验证码用）
        public static final String SEND_MESSAGE = BASE_URL + "Canteen/sendMessage/random";
    }

    public static  class KEY{
        public static final String USER_NAME="workerName";
        public static final String USER_CODE="code";
        public static final String USER_PHOTO="photo";
        public static final String USER_PHONE="phoneNumber";
        public static final String WORK_ID="workerId";
        public static final String USER_ACCOUNT="account";

    }

    public static  class CODE{

    }



    public static String getDeviceID(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }
}
