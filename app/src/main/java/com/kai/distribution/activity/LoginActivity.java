package com.kai.distribution.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.entity.UserInfo;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginActivity extends Activity implements OnClickListener{

	private EditText et_user;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_register;
	private TextView tv_forget;
	private ProgressDialog dialog;
	private UserInfo userInfo;
	private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		if(!RsSharedUtil.getString(LoginActivity.this,Constants.KEY.USER_CODE).equals(""))
		{
			Log.e("debug","跳过登陆界面");
			Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_login);
        initView();
        initOnClick();
		tv_forget.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
		tv_register.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

	private void initView() {
		// TODO Auto-generated method stub
		et_user=(EditText) findViewById(R.id.accountnumber_Et);
		et_password=(EditText) findViewById(R.id.password_Et);
		btn_login=(Button) findViewById(R.id.land_Btn);
		tv_forget=(TextView) findViewById(R.id.forgetnumber_Tv);
		tv_register=(TextView) findViewById(R.id.register_Tv);
	}

	private void initOnClick() {
		// TODO Auto-generated method stub
		btn_login.setOnClickListener(this);
		tv_forget.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent=null;
		switch(view.getId())
		{
			case R.id.land_Btn:
				String user=et_user.getText().toString().trim();
				String password=et_password.getText().toString().trim();
				if (user.equals("")||password.equals(""))
				{
					Toast.makeText(this,"请把信息填写完整！！",Toast.LENGTH_SHORT).show();
					return;
				}
				dialog=new ProgressDialog(this);
				dialog.setMessage("正在登录...");
				dialog.setCancelable(true);
				dialog.show();

				postLogin(user,password);
//				Intent intent1 = new Intent(this,HomeActivity.class);
//				startActivity(intent1);
				break;

			case R.id.forgetnumber_Tv:
				intent=new Intent(LoginActivity.this,ForgetNumberActivity.class);
				startActivity(intent);
				break;

			case R.id.register_Tv:
				intent=new Intent(LoginActivity.this,Register_Activity.class);
				startActivity(intent);
				break;
		}
	}

	// 登录
	private void postLogin(final String user, String password) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("account", user);
			jsonObject.put("password", password);
			jsonObject.put("recognizeCode", Constants.getDeviceID(this));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
				Constants.URL.LOGIN_URL, jsonObject, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.e(TAG,"login: "+response);
				dialog.dismiss();

				try {

					JSONObject jsonObject = new JSONObject(response);
					String res = jsonObject.getString("result");
					Log.e(TAG,res);
					if (res.equals("alreadylogined")) {
						Log.e(TAG,"alreadylogined:::");
						Toast.makeText(LoginActivity.this, "配送员已经登录", Toast.LENGTH_LONG).show();
					}else if(res.equals("success")){
						Log.e(TAG,"success");
						Toast.makeText(LoginActivity.this,"登录成功！！",Toast.LENGTH_SHORT).show();
						userInfo =new ObjectMapper().readValue(response, UserInfo.class);
						if (userInfo!=null)
						{
							Log.d("loginUserInfo",userInfo.toString());
							RsSharedUtil.putString(LoginActivity.this,Constants.KEY.USER_ACCOUNT,userInfo.getAccount());
							RsSharedUtil.putString(LoginActivity.this,Constants.KEY.USER_NAME,userInfo.getWorkerName());
							RsSharedUtil.putString(LoginActivity.this,Constants.KEY.USER_CODE,userInfo.getCode());
							RsSharedUtil.putString(LoginActivity.this,Constants.KEY.USER_PHOTO,userInfo.getPhoto());
							RsSharedUtil.putString(LoginActivity.this,Constants.KEY.USER_PHONE,userInfo.getPhoneNumber());
							RsSharedUtil.putInt(LoginActivity.this,Constants.KEY.WORK_ID,userInfo.getWorkerId());
							Intent intent=new Intent();
							intent.setClass(LoginActivity.this,HomeActivity.class);
							startActivity(intent);
							finish();
						}else if(res.equals("no such a worker")){
							Log.e(TAG,"no such a worker");
							Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
						}else if(res.equals("wrongformat")){
							Log.e(TAG,"wrongformat");
							Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
						}else {
							Log.e(TAG,"else");
							Toast.makeText(LoginActivity.this, res, Toast.LENGTH_SHORT).show();
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG,e.toString());
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Log.d("loginError",error.toString());
				Toast.makeText(LoginActivity.this,"登录失败！！",Toast.LENGTH_SHORT).show();
			}

		});
		stringRequest.setTag("LoginActivity");
		MyApplication.getRequestQueue().add(stringRequest);
	}

	@Override
	protected void onDestroy()
	{
		MyApplication.getRequestQueue().cancelAll("LoginActivity");
		super.onDestroy();
	}
}
