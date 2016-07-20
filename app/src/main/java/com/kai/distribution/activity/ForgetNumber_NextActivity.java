package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetNumber_NextActivity extends Activity
{
    private ImageButton return_to_ForgetNumberActivity_Btn;
    private EditText new_password_Et,new_password_again_Et;
    private Button forgetnumber_comfirm_Btn;

    private static final String TAG = "ForgetNumber_Next";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgetnumber_tonext);
        initView();
        initOnClick();
    }

    private void initView() {
        // TODO Auto-generated method stub
        return_to_ForgetNumberActivity_Btn=(ImageButton) findViewById(R.id.return_to_ForgetNumberActivity_Btn);
        new_password_Et=(EditText) findViewById(R.id.new_password_Et);
        new_password_again_Et=(EditText) findViewById(R.id.new_password_again_Et);
        forgetnumber_comfirm_Btn=(Button) findViewById(R.id.forgetnumber_comfirm_Btn);
    }


    private void initOnClick() {
        // TODO Auto-generated method stub
        return_to_ForgetNumberActivity_Btn.setOnClickListener(click);
        forgetnumber_comfirm_Btn.setOnClickListener(click);
    }

    private OnClickListener click=new OnClickListener() {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            switch(view.getId())
            {
                case R.id.return_to_ForgetNumberActivity_Btn:
                    finish();
                    break;

                case R.id.forgetnumber_comfirm_Btn:
                    if (TextUtils.isEmpty(new_password_Et.getText().toString())){
                        Toast.makeText(ForgetNumber_NextActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(new_password_again_Et.getText().toString())){
                        Toast.makeText(ForgetNumber_NextActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    }else if (!new_password_Et.getText().toString().equals(new_password_again_Et.getText().toString())){
                        Toast.makeText(ForgetNumber_NextActivity.this, "两次新密码不一致", Toast.LENGTH_SHORT).show();
                    }else{
                        String phoneNumber = getIntent().getStringExtra("phoneNum");
                        Log.e(TAG,"phoneNumber:" + phoneNumber);
                        Log.e(TAG,"newPassword:" + new_password_Et.getText().toString());
                        findPassword(phoneNumber,new_password_Et.getText().toString(),Constants.getDeviceID(ForgetNumber_NextActivity.this));
                    }
                    break;



            }
        }
    };



    private void findPassword(String phoneNumber,String newPassword,String recognizeCode){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNumber",phoneNumber);
            jsonObject.put("newPassword",newPassword);
            jsonObject.put("recognizeCode",recognizeCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
                Constants.URL.FIND_PASSWORD, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,"response" + response.toString());

                try {
                    JSONObject result = new JSONObject(response);
                    try {
                        if (result.get("result").equals("wrongphonenumber")) {
                            Toast.makeText(ForgetNumber_NextActivity.this, "该手机号未绑定", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    catch (JSONException e) {
                        Toast.makeText(ForgetNumber_NextActivity.this, "更改密码成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetNumber_NextActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"error: " + error.toString());
            }
        });

        MyApplication.getRequestQueue().add(stringRequest);
    }
}
