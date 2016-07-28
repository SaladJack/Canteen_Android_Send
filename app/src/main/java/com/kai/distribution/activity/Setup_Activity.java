package com.kai.distribution.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.utils.DataCleanManager;
import com.kai.distribution.utils.NetResultUtils;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class Setup_Activity extends Activity implements OnClickListener
{
	private ImageButton tv_return;
	private LinearLayout ll_version;
	private LinearLayout ll_clear;
	private Button btn_logout;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_up);
		initView();
		initClick();
	}

	private void initView() {
		tv_return=(ImageButton) findViewById(R.id.set_up_return);
		ll_version=(LinearLayout) findViewById(R.id.version);
		ll_clear=(LinearLayout) findViewById(R.id.clear_cache);
		btn_logout=(Button) findViewById(R.id.set_up_back);
	}
	

	private void initClick() {
		tv_return.setOnClickListener(this);
		ll_version.setOnClickListener(this);
		ll_clear.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

			switch(view.getId())
			{
				case R.id.set_up_return:
					finish();
					break;

				case R.id.version:
					Intent intent=new Intent(Setup_Activity.this,Version_Activity.class);
					startActivity(intent);
					break;

				case R.id.clear_cache:
					final AlertDialog alertDialog = new AlertDialog.Builder(Setup_Activity.this).create();
					alertDialog.show();
					Window window = alertDialog.getWindow();
					window.setContentView(R.layout.clear_cache_diaolog);
					TextView clear_cache_comfirm=(TextView) window.findViewById(R.id.clear_cache_comfirm);
					TextView clear_cache_cancel=(TextView) window.findViewById(R.id.clear_cache_cancel);
					clear_cache_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							alertDialog.dismiss();
						}
					});
					clear_cache_comfirm.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							alertDialog.dismiss();





							DataCleanManager.cleanUnusefulData(getApplicationContext());
							Toast.makeText(Setup_Activity.this, "已清除缓存", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(Setup_Activity.this,HomeActivity.class);
							startActivity(intent);
							finish();



						}
					});

					break;

				case R.id.set_up_back:
					final AlertDialog alertDialog2 = new AlertDialog.Builder(Setup_Activity.this).create();
					alertDialog2.show();
					Window window2 = alertDialog2.getWindow();
					window2.setContentView(R.layout.comfirm_back_dialog);
					TextView set_up_return_yes=(TextView) window2.findViewById(R.id.set_up_return_yes);
					TextView set_up_return_no=(TextView) window2.findViewById(R.id.set_up_return_no);
					set_up_return_yes.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							alertDialog2.dismiss();
							logout();
						}
					});
					set_up_return_no.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							alertDialog2.dismiss();
						}
					});
					break;
			}
	}


	// 登录
	private void logout() {
		dialog=new ProgressDialog(this);
		dialog.setMessage("正在退出...");
		dialog.setCancelable(true);
		dialog.show();

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("code", RsSharedUtil.getString(Setup_Activity.this, Constants.KEY.USER_CODE));
			jsonObject.put("workerId", RsSharedUtil.getInt(Setup_Activity.this, Constants.KEY.WORK_ID));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AppStringRequest stringRequest = new AppStringRequest(com.android.volley.Request.Method.POST,
				Constants.URL.LOGOUT_URL, jsonObject, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d("loginOutSuccess", response );
				dialog.dismiss();

				try {
					JSONObject jsonObject=new JSONObject(response);
					String status=jsonObject.getString("result");
					if (status.equals("logout successfully"))
					{
						NetResultUtils.logout(Setup_Activity.this);
					}
					else if(status.equals("no such a student"))
					{
						NetResultUtils.noSuchAStudent(Setup_Activity.this);
					}
					else if(status.equals("offline"))
					{
						NetResultUtils.offLine(Setup_Activity.this);
					}
					else if(status.equals("longtimeoffline"))
					{
						NetResultUtils.longTimeOffLine(Setup_Activity.this);

					}
					else if(status.equals("wrongcode")){
						NetResultUtils.wrongCode(Setup_Activity.this);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}


			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Log.d("logoutError", "error");
				Toast.makeText(Setup_Activity.this, "服务器错误，退出失败！！", Toast.LENGTH_SHORT).show();
			}

		});
		stringRequest.setTag("Setup_Activity");
		MyApplication.getRequestQueue().add(stringRequest);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.getRequestQueue().cancelAll("Setup_Activity");
	}
}
