package com.kai.distribution.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Bind_Phone_Activity extends Activity
{
    private ImageButton bind_phone_return;
    private EditText phone_input;
    private EditText code_input;
    private Button waiting_code;
    private Button bing_phone_comfirm;
    private boolean isSendMsg = false; //短信是否已发送


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

    private OnClickListener click=new OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.bind_phone_return:
                    finish();
                    break;

                case R.id.bing_phone_comfirm:
                    if (isSendMsg){
                        if (!TextUtils.isEmpty(code_input.getText().toString())){
                            bindPhone(RsSharedUtil.getString(Bind_Phone_Activity.this,Constants.KEY.USER_CODE),
                                      RsSharedUtil.getInt(Bind_Phone_Activity.this,Constants.KEY.WORK_ID),
                                      code_input.getText().toString(),
                                      phone_input.getText().toString());
                        }
                    }
                    else {
                        Toast.makeText(Bind_Phone_Activity.this, "请先获得验证码", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.waiting_code:
                    if (phone_input.getText().toString().length() == 11){
                        isSendMsg = true;
                        sendMessage(phone_input.getText().toString());
                    }
                    else{
                        isSendMsg = false;
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
                    if (result.get("result").equals("sucess")){
                        Toast.makeText(Bind_Phone_Activity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
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
                    if (result.get("result").equals("sucess")){
                        Toast.makeText(Bind_Phone_Activity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                        RsSharedUtil.putString(Bind_Phone_Activity.this,Constants.KEY.USER_PHONE,phoneNum);
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

}
