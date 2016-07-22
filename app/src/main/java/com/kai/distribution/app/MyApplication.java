package com.kai.distribution.app;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kai.distribution.activity.LoginActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dell on 2016/6/22.
 */
public class MyApplication extends Application {

    // 全局的volleyRequest
    static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        // 初始化全局的volleyrequest
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        super.onCreate();
    }
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }



}
