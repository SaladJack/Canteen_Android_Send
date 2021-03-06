package com.kai.distribution.app;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.kai.distribution.entity.Distributed;
import com.kai.distribution.entity.Distributing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * author:hh
 * Created by Dell on 2016/6/21.
 * 此类用来存储常量，URL，Key等
 */
public class Constants {

    //SharePreference的存储路径
    public static final String SHARED_PATH = "re_share";
    public static final int REFRESH_REQUEST = 1;

    public static  class URL{
        //        public static final String BASE_URL="http://milk345.imwork.net:13607/";
        public static final String BASE_URL="http://192.168.0.112:8080/";

        //登录
        public static final String LOGIN_URL=BASE_URL+"Canteen/worker/login";
        //退出
        public static final String LOGOUT_URL=BASE_URL+"Canteen/worker/logout";
        //认证
        public static final String REGISTER_URL=BASE_URL+"Canteen/worker/setPassword";
        //已送达订单
        public static final String DISTRIBUTED_URL = BASE_URL+"Canteen/worker/showSendedOrders";
        //绑定手机
        public static final String BIND_PHONE = BASE_URL + "Canteen/worker/bindPhone";
        //发送短信（获得验证码用）
        public static final String SEND_MESSAGE = BASE_URL + "Canteen/sendMessage/random";
        //验证验证码是否正确
        public static final String CONFIRM_CODE = BASE_URL + "Canteen/worker/confirmCode";
        //显示待送餐
        public static final String DISTRIBUTING_URL = BASE_URL + "Canteen/worker/showOrders";
        //修改密码
        public static final String CHANGE_PASSWORD = BASE_URL + "Canteen/worker/modifyPassword";
        //工作汇总接口
        public static final String FIND_WORK_SUMMARY = BASE_URL + "Canteen/worker/findWorkSummary";
        //找回密码
        public static final String FIND_PASSWORD = BASE_URL + "Canteen/worker/findPassword";
        //取消绑定手机
        public static final String  CANCEL_BIND_PHONE = BASE_URL + "Canteen/worker/cancelBindPhone";
        //显示系统消息
        public static final String SHOW_MESSAGES = BASE_URL + "Canteen/worker/showMessages";
        //设置配送员接口
        public static final String CHANGE_STATU = BASE_URL + "Canteen/worker/changeStatu";
        //确认订单
        public static final String CONFIRM_ORDER = BASE_URL + "Canteen/worker/confirmOrder";
    }

    public static  class KEY{
        public static final String USER_NAME="workerName";
        public static final String USER_CODE="code"; //令牌
        public static final String USER_PHOTO="photo";
        public static final String USER_PHONE="phoneNumber";
        public static final String WORK_ID="workerId";
        public static final String USER_ACCOUNT="account"; //账号

        public static final String USER_HAVESCANNED = "havescanned";
    }

    public static  class CODE{
        public static final int HAVE_DISTRIBUTING = 1;
        public static final int WAITING = 2;
        public static final int SCAN = 3;
    }

    public static class GLOBAL {
        public static List<Distributing> distributingList = new ArrayList<>();
        public static List<Distributing> newDatas = new ArrayList<>();
        public static int DISTRIBUTING_NUM = 0;
        public static JSONArray unparsedNewDatas;
        public static boolean UPDATE_FRAGMENT = false;
        public static boolean HAVE_SCANNED = false;


        public static List<Distributed> distributedList = new ArrayList<>();
    }



    public static String getDeviceID(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }
}
