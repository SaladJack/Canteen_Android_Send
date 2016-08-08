package com.kai.distribution.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tusm on 16/7/20.
 */
public class DistrbutingUtils {

    private static final String TAG = "DistrbutingUtils";
    public static void getDistributedListByHTTP(final Context context, int buildingId){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("code", RsSharedUtil.getString(context,"code"));
            jsonObject.put("workerId", RsSharedUtil.getInt(context,Constants.KEY.WORK_ID));
            jsonObject.put("buildingId", buildingId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST, Constants.URL.DISTRIBUTING_URL, jsonObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Logger.json(response.toString());
                            String res = response.getJSONObject(0).getString("result");
                            if(res.equals("success")) {
                                Log.e("Fragment_Distributing", response.toString());


                                Constants.GLOBAL.unparsedNewDatas = response;
//                                Constants.GLOBAL.unparsedNewDatas = Constants.getTestResponse();
                                manageData(context);//处理数据
                            }else if (res.equals("noorder")){
                                Toast.makeText(context, "没有订单数据", Toast.LENGTH_SHORT).show();
                            }else{
                                NetResultUtils.badResponse(res, (Activity) context);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();

                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fragment_Distributing","error:"+error.toString());
                //Constants.GLOBAL.unparsedNewDatas = null;
            }
        });

        MyApplication.getRequestQueue().add(jsonArrayRequest);
    }

    public static void manageData(Context context){
        if (Constants.GLOBAL.newDatas != null){
            Constants.GLOBAL.newDatas.clear();
        }else {
            Constants.GLOBAL.newDatas = new ArrayList<>();
        }

        if (Constants.GLOBAL.unparsedNewDatas == null || Constants.GLOBAL.unparsedNewDatas.length()==0){
            // FIXME: 16/7/20
            Toast.makeText(context,"没有代送餐记录",Toast.LENGTH_SHORT).show();
            Constants.GLOBAL.DISTRIBUTING_NUM = 0;
            return;
        } else {

            Log.e(TAG,"JsonToBean");
            //解析成Bean
            Constants.GLOBAL.newDatas = JsonToBean.getDistributings(Constants.GLOBAL.unparsedNewDatas.toString());
            Constants.GLOBAL.DISTRIBUTING_NUM = Constants.GLOBAL.newDatas.size();
            Log.e(TAG,TAG+" : DISTRIBUTING_NUM: " + Constants.GLOBAL.DISTRIBUTING_NUM);



            Constants.GLOBAL.unparsedNewDatas = null;


            Constants.GLOBAL.distributingList.clear();
            Constants.GLOBAL.distributingList.addAll(Constants.GLOBAL.newDatas);
//            listview_adapter.notifyDataSetChanged();

            Logger.e("haveScanned" + Constants.GLOBAL.HAVE_SCANNED);
            if (Constants.GLOBAL.HAVE_SCANNED && Constants.GLOBAL.DISTRIBUTING_NUM > 0) {
                Log.e(TAG,"sendArea:"+ Constants.GLOBAL.newDatas.get(0).getSendArea());
                Message msg = Message.obtain();
                msg.what = Constants.CODE.HAVE_DISTRIBUTING;
                EventBus.getDefault().post(msg);

                EventBus.getDefault().post(Constants.GLOBAL.newDatas);

            }else if(Constants.GLOBAL.HAVE_SCANNED && Constants.GLOBAL.DISTRIBUTING_NUM == 0){
                Message msg = Message.obtain();
                msg.what = Constants.CODE.WAITING;
                EventBus.getDefault().post(msg);
            }
        }

        }

    }


