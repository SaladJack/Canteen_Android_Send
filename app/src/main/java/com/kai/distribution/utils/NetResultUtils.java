package com.kai.distribution.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kai.distribution.activity.HomeActivity;
import com.kai.distribution.activity.LoginActivity;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;

/**
 * Created by tusm on 16/7/20.
 */
public class NetResultUtils {


    public static void badResponse(String res,Activity activity){
        if (res.equals("no such a worker")){
            NetResultUtils.noSuchAStudent(activity);
        }else if (res.equals("offline")){
            NetResultUtils.offLine(activity);
        }else if(res.equals("wrongcode")){
            NetResultUtils.wrongCode(activity);
        }else if (res.equals("longtimeoffline")){
            NetResultUtils.longTimeOffLine(activity);
        }

    }


    public static void offLine(Activity activity){
        Toast.makeText(activity, "你已离线，系统自动退出", Toast.LENGTH_SHORT).show();
        clearData(activity);

    }

    public static void noSuchAStudent(Activity activity){
        Toast.makeText(activity, "该配送员不存在，系统自动退出", Toast.LENGTH_SHORT).show();
        clearData(activity);
    }


    public static void logout(Activity activity){
        Toast.makeText(activity, "退出成功", Toast.LENGTH_SHORT).show();
        clearData(activity);
    }

    public static void longTimeOffLine(Activity activity){
        Toast.makeText(activity, "配送员30分钟不在线，系统自动退出", Toast.LENGTH_SHORT).show();
        clearData(activity);
    }


    public static void wrongCode(Activity activity){
        Toast.makeText(activity, "令牌错误，系统自动退出", Toast.LENGTH_SHORT).show();
        clearData(activity);
    }

    private static void clearData(Activity activity){
        RsSharedUtil.putString(activity, Constants.KEY.USER_ACCOUNT,"");
        RsSharedUtil.putString(activity, Constants.KEY.USER_NAME, "");
        RsSharedUtil.putString(activity, Constants.KEY.USER_CODE, "");
        RsSharedUtil.putString(activity, Constants.KEY.USER_PHOTO,"");
        RsSharedUtil.putString(activity, Constants.KEY.USER_PHONE, "");
        RsSharedUtil.putInt(activity, Constants.KEY.WORK_ID, 0);
        if (HomeActivity.mHomeActivity != null) {
            HomeActivity.mHomeActivity.finish();
        }
        Intent intent = new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);




    }


}
