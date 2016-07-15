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
    }

    public static  class KEY{
        public static final String USER_NAME="user_name";
        public static final String USER_CODE="user_code";
        public static final String USER_PHOTO="user_photo";
        public static final String USER_PHONE="user_phone";
        public static final String WORK_ID="user_work_id";
        public static final String USER_ACCOUNT="user_account";

    }

    public static  class CODE{

    }



    public static String getDeviceID(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }
}
