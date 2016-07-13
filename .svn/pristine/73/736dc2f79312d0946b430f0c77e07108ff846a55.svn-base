package com.kai.distribution.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.utils.RsSharedUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

@ContentView(R.layout.activity_sys_message)
public class SysMessage_Activity extends Activity
{
	@ViewInject(R.id.tv_back)
	private TextView tv_back;
	@ViewInject(R.id.lv_message)
	private ListView lv_message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		initView();

		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void initView()
	{
		getMessage();
	}


	// 获取系统消息
	private void getMessage() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("pageIndex", 1);
			jsonObject.put("code", RsSharedUtil.getString(SysMessage_Activity.this, Constants.KEY.USER_CODE));
			jsonObject.put("workerId", RsSharedUtil.getInt(SysMessage_Activity.this, Constants.KEY.WORK_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
				Constants.URL.SYS_MESSAGE_URL, jsonObject, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d("getMessageSuccess", response );



			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

				Toast.makeText(SysMessage_Activity.this, "服务器错误，获取系统消息失败！！", Toast.LENGTH_SHORT).show();
			}

		});
		stringRequest.setTag("Sys_Activity");
		MyApplication.getRequestQueue().add(stringRequest);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.getRequestQueue().cancelAll("Sys_Activity");
	}


}
