package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.utils.DistrbutingUtils;
import com.kai.distribution.utils.NetResultUtils;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Bind_Phone_Activity extends Activity
{
    private ImageButton bind_phone_return;
    private EditText phone_input;
    private EditText code_input;
    private Button waiting_code;
    private Button bing_phone_comfirm;
    private boolean haveSendedMsg = false; //短信是否已发送
    private boolean haveClicked = false;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time >= 0) {
                waiting_code.setText("等待" + time + "秒");
            } else {
                waiting_code.setText("发送验证码");
                waiting_code.setEnabled(true);
            }
        }
    };

    private static final String TAG = "Bind_Phone_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bind_phone);
        initView();
        initClick();
    }

    private void initView() {
        bind_phone_return=(ImageButton) findViewById(R.id.bind_phone_return);
        phone_input=(EditText) findViewById(R.id.phone_input);
        code_input=(EditText) findViewById(R.id.code_input);
        waiting_code=(Button) findViewById(R.id.waiting_code);
        bing_phone_comfirm=(Button) findViewById(R.id.bing_phone_comfirm);
    }


    private void initClick() {
        bind_phone_return.setOnClickListener(click);
        waiting_code.setOnClickListener(click);
        bing_phone_comfirm.setOnClickListener(click);
    }

    private int time = 100;
    private OnClickListener click=new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.bind_phone_return:
                    finish();
                    break;

                case R.id.bing_phone_comfirm:
                    if (haveSendedMsg && haveClicked){
                        if (!TextUtils.isEmpty(code_input.getText().toString())){
                                confirmCode(code_input.getText().toString(), phone_input.getText().toString());
                        }
                    }
                    else {
                        Toast.makeText(Bind_Phone_Activity.this, "请先获得验证码", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.waiting_code:
                    if (phone_input.getText().toString().length() == 11){
                        haveClicked = true;
                        haveSendedMsg = true;
                        waiting_code.setEnabled(false);
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (time >= 0) {
                                    handler.sendEmptyMessage(time);
                                    --time;
                                }else if(time<0&&time==100) {
                                    time = 100;
                                    handler.sendEmptyMessage(time);
                                    haveClicked = false;
                                    timer.cancel();
                                }
                            }
                        },0,1000);
                        sendMessage(phone_input.getText().toString());
                    }
                    else{
                        haveSendedMsg = false;
                        Toast.makeText(Bind_Phone_Activity.this, "手机号输入错误", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    /*

    发送验证码
    发送验证码接口
    url：http://milk345.imwork.net:13607/Canteen/sendMessage/random
    方法：POST
    入参：
     {
    "phoneNum":电话号码（string）
    }
    Content-Type: application/json
    描述：
    状态200返回值
    {
    "result":"success"（发送成功）
    }
    */
    private void sendMessage(String phoneNum){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNum",phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
                Constants.URL.SEND_MESSAGE, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Bind_Phone_Activity","sendMessage()_response" + response.toString());

                try {
                    JSONObject result = new JSONObject(response);
                    if (result.getString("result").equals("sucess")){
                        Toast.makeText(Bind_Phone_Activity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        NetResultUtils.badResponse(result.getString("result"),Bind_Phone_Activity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Bind_Phone_Activity","sendMessage()_error" + error.toString());
            }
        });

        MyApplication.getRequestQueue().add(stringRequest);
    }


    /*
    绑定手机接口
url：http://milk345.imwork.net:13607/Canteen/worker/bindPhone
方法：POST
入参：
 {
"code":不含时间戳的令牌（string）
" workerId ":配送员Id（int）
" randomNumber":短信验证码（string）
" phoneNum":手机号（string）
}
Content-Type: application/json
描述：con
状态200返回值
{
"result":"success"(成功)
"result":"no such a student"(ID错误)
"result":"offline"(配送员不在线)
"result":"wrongcode"(令牌错误)
"result":"longTimeOffLine"(太长时间没操作掉线了)
"result":" nullcode"(验证码为空)
"result":" wrongcode"(验证码错误)
}
     */
    private void bindPhone(String code, int workedId, String randomNumber, final String phoneNum){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("code",code);
            jsonObject.put("workerId",workedId);
            jsonObject.put("randomNumber",randomNumber);
            jsonObject.put("phoneNum",phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
                Constants.URL.BIND_PHONE, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Bind_Phone_Activity","bindPhone()_response" + response.toString());

                try {
                    JSONObject result = new JSONObject(response);
                    if (result.getString("result").equals("success")){
                        Toast.makeText(Bind_Phone_Activity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                        RsSharedUtil.putString(Bind_Phone_Activity.this,Constants.KEY.USER_PHONE,phoneNum);
                        Intent intent = new Intent(Bind_Phone_Activity.this,HomeActivity.class);
                        startActivityForResult(intent,Constants.REFRESH_REQUEST);
                        finish();
                    }else {
                            NetResultUtils.badResponse(result.getString("result"),Bind_Phone_Activity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Bind_Phone_Activity","bindPhone()_error" + error.toString());
            }
        });

        MyApplication.getRequestQueue().add(stringRequest);

    }


    private void confirmCode(String code,String phoneNumber){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("code",code);
            jsonObject.put("phoneNumber",phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.POST, Constants.URL.CONFIRM_CODE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG,"response: " + response.toString());
                try {
                    String res = response.getString("result");
                    if (res.equals("success")){
                        bindPhone(RsSharedUtil.getString(Bind_Phone_Activity.this,Constants.KEY.USER_CODE),
                                RsSharedUtil.getInt(Bind_Phone_Activity.this,Constants.KEY.WORK_ID),
                                code_input.getText().toString(),
                                phone_input.getText().toString());
                    }else if (res.equals("nullcode")){
                        Toast.makeText(Bind_Phone_Activity.this, "验证码为空", Toast.LENGTH_SHORT).show();
                    }else if (res.equals("wrongcode")){
                        Toast.makeText(Bind_Phone_Activity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyApplication.getRequestQueue().add(jsonObjectRequest);

    }
}
