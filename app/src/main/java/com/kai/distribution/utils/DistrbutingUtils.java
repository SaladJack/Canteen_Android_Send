package com.kai.distribution.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;

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
        String url = Constants.URL.DISTRIBUTING_URL;

        try {

            jsonObject.put("code", RsSharedUtil.getString(context,"code"));
            jsonObject.put("workerId", RsSharedUtil.getInt(context,Constants.KEY.WORK_ID));
            jsonObject.put("buildingId", buildingId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("Fragment_Distributing", response.toString());
                            Constants.GLOBOL.unparsedNewDatas = response;
                            manageData(context);//处理数据
                            Log.i("onResponse", "success");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            String result = response.toString();

                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fragment_Distributing","error:"+error.toString());
                //Constants.GLOBOL.unparsedNewDatas = null;
            }
        });

        MyApplication.getRequestQueue().add(jsonArrayRequest);
    }

    public static void manageData(Context context){
        if (Constants.GLOBOL.newDatas != null){
            Constants.GLOBOL.newDatas.clear();
        }else {
            Constants.GLOBOL.newDatas = new ArrayList<>();
        }

        if (Constants.GLOBOL.unparsedNewDatas == null || Constants.GLOBOL.unparsedNewDatas.length()==0){
            // FIXME: 16/7/20
            Toast.makeText(context,"没有代送餐记录",Toast.LENGTH_SHORT).show();
            Constants.GLOBOL.distributingNum = 0;
            return;
        } else {

            Log.e(TAG,"JsonToBean");
            //解析成Bean
            Constants.GLOBOL.newDatas = JsonToBean.getDistributings(Constants.GLOBOL.unparsedNewDatas.toString());
            Constants.GLOBOL.distributingNum = Constants.GLOBOL.newDatas.size();
            Constants.GLOBOL.unparsedNewDatas = null;


            Constants.GLOBOL.distributingList.clear();
            Constants.GLOBOL.distributingList.addAll(Constants.GLOBOL.newDatas);
//            listview_adapter.notifyDataSetChanged();

        }

    }

}
