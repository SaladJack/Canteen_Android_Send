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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetNumberActivity extends Activity 
{
	private EditText forgetnumber_phone_Et,identifynumber_Et;
	private Button send_identifynumber_Btn,forgetnumber_to_next_Btn;
	private ImageButton return_to_mainactivity_Btn;
	private Intent intent;
	private Timer timer;
	private int time = 100;
	private static final String TAG = "ForgetNumberActivity";



	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (time >= 0) {
				send_identifynumber_Btn.setText("等待" + time + "秒");
			}else if(time<0&&time==100){
				send_identifynumber_Btn.setText("发送验证码");
				send_identifynumber_Btn.setEnabled(true);
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgetnumber);
		
		initView();
		initOnClick();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	private void initView() {
		// TODO Auto-generated method stub
		forgetnumber_phone_Et=(EditText) findViewById(R.id.forgetnumber_phone_Et);
		identifynumber_Et=(EditText) findViewById(R.id.identifynumber_Et);
		send_identifynumber_Btn=(Button) findViewById(R.id.send_identifynumber_Btn);
		forgetnumber_to_next_Btn=(Button) findViewById(R.id.forgetnumber_to_next_Btn);
		return_to_mainactivity_Btn=(ImageButton) findViewById(R.id.return_to_mainactivity_Btn);
	}


	private void initOnClick() {
		// TODO Auto-generated method stub
		send_identifynumber_Btn.setOnClickListener(forgetnumber_click);
		forgetnumber_to_next_Btn.setOnClickListener(forgetnumber_click);
		return_to_mainactivity_Btn.setOnClickListener(forgetnumber_click);
	}
	
	private OnClickListener forgetnumber_click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.return_to_mainactivity_Btn:
				finish();
				break;
			case R.id.send_identifynumber_Btn:
				if (TextUtils.isEmpty(forgetnumber_phone_Et.getText().toString())){
					Toast.makeText(ForgetNumberActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
				} else{
					send_identifynumber_Btn.setEnabled(false);
					timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							if (time >= 0) {
								handler.sendEmptyMessage(time);
								--time;
							}else{
								handler.sendEmptyMessage(time);
								time = 100;
								timer.cancel();
							}
						}
					},0,1000);


					sendMessage(forgetnumber_phone_Et.getText().toString());
				}

				break;
				
			case R.id.forgetnumber_to_next_Btn:
				if (TextUtils.isEmpty(forgetnumber_phone_Et.getText().toString())){
					Toast.makeText(ForgetNumberActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(identifynumber_Et.getText().toString())){
					Toast.makeText(ForgetNumberActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
				}
				else {
					intent = new Intent(ForgetNumberActivity.this, ForgetNumber_NextActivity.class);
					intent.putExtra("phoneNum", forgetnumber_phone_Et.getText().toString());
					startActivity(intent);
					break;
				}
			}
		}
	};

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
				Log.e(TAG,"sendMessage()_response" + response.toString());

				try {
					JSONObject result = new JSONObject(response);
					if (result.get("result").equals("sucess")){
						Toast.makeText(ForgetNumberActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG,"sendMessage()_error" + error.toString());
			}
		});

		MyApplication.getRequestQueue().add(stringRequest);
	}




	@Override
	protected void onStop() {
		super.onStop();
		if (timer !=null){
			timer.cancel();
		}
		handler.removeMessages(time);
		handler = null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
