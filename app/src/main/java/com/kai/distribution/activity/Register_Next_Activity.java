package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Register_Next_Activity extends Activity {
	private ImageView register_next_back;
	private EditText register_password, register_passowd_again;
	private Button register_next_success;
	private UserInfo userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_next);

		initView();


	}


	private void initView() {
		register_next_back = (ImageView) findViewById(R.id.register_next_back);
		register_password = (EditText) findViewById(R.id.register_password);
		register_passowd_again = (EditText) findViewById(R.id.register_password_again);
		register_next_success = (Button) findViewById(R.id.register_next_success);

		register_next_back.setOnClickListener(click);
		register_next_success.setOnClickListener(click);
	}

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
				case R.id.register_next_back:
					finish();
					break;


				case R.id.register_next_success:
					if (register_password.getText().toString().equals(register_passowd_again.getText().toString())) {
						Intent intent = getIntent();
						Log.e("Register_Next_Activity","认证按钮被点击");
						register(intent.getStringExtra("account"), register_password.getText().toString(), intent.getStringExtra("name"));
					} else {
						Toast.makeText(Register_Next_Activity.this, "两次输入的密码不相同！", Toast.LENGTH_SHORT).show();
						register_passowd_again.setText("");
					}


					break;

			}
		}
	};





	private void register(final String account, final String password, String name) {
		JSONObject jsonObject = new JSONObject();
		Log.e("Register_Next_Activity","register()");
		try {
			jsonObject.put("account", account);
			jsonObject.put("newPassword", password);
			jsonObject.put("name", name);
			jsonObject.put("recognizeCode", Constants.getDeviceID(this));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}

		AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
				Constants.URL.REGISTER_URL, jsonObject, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e("Register_Next_Activity","回应为"+response.toString());
				Log.e("Register_Next_Activity","reigster请求已有回应");
				postLogin(account,password);

			}


		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		stringRequest.setTag("register");
		MyApplication.getRequestQueue().add(stringRequest);
	}


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
				Intent intent=new Intent();
				try {

					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.getString("result").equals("alreadylogined")) {
						intent.setClass(Register_Next_Activity.this,HomeActivity.class);
						startActivity(intent);
						finish();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				try{
					userInfo =new ObjectMapper().readValue(response, UserInfo.class);
					if (userInfo!=null)
					{
						RsSharedUtil.putString(Register_Next_Activity.this,Constants.KEY.USER_ACCOUNT,userInfo.getAccount());
						RsSharedUtil.putString(Register_Next_Activity.this,Constants.KEY.USER_NAME,userInfo.getWorkerName());
						RsSharedUtil.putString(Register_Next_Activity.this,Constants.KEY.USER_CODE,userInfo.getCode());
						RsSharedUtil.putString(Register_Next_Activity.this,Constants.KEY.USER_PHOTO,userInfo.getPhoto());
						RsSharedUtil.putString(Register_Next_Activity.this,Constants.KEY.USER_PHONE,userInfo.getPhoneNumber());
						RsSharedUtil.putInt(Register_Next_Activity.this,Constants.KEY.WORK_ID,userInfo.getWorkerId());
						intent.setClass(Register_Next_Activity.this,HomeActivity.class);
						startActivity(intent);
						finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d("loginError",error.toString());
			}

		});
		stringRequest.setTag("Register_Next_Activity");
		MyApplication.getRequestQueue().add(stringRequest);
	}


}
