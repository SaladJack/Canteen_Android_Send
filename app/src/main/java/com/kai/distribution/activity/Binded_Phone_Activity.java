package com.kai.distribution.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.utils.NetResultUtils;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class Binded_Phone_Activity extends Activity
{
	private TextView binded_phone_number;
	private ImageButton binded_phone_back;
	private Button unbinded_phone_number;
	private static final String TAG = "Binded_Phone_Activity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.binded_phone);

		binded_phone_back=(ImageButton) findViewById(R.id.binded_phone_back);
		binded_phone_number=(TextView) findViewById(R.id.binded_phone_number);
		unbinded_phone_number=(Button) findViewById(R.id.unbinded_phone_number);

		binded_phone_number.setText(RsSharedUtil.getString(this, Constants.KEY.USER_PHONE));

		binded_phone_back.setOnClickListener(click);
		unbinded_phone_number.setOnClickListener(click);
	}
	private OnClickListener click=new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
				case R.id.binded_phone_back:
					finish();
					break;

				case R.id.unbinded_phone_number:
					final AlertDialog alertDialog = new AlertDialog.Builder(Binded_Phone_Activity.this).create();
					alertDialog.show();
					Window window = alertDialog.getWindow();
					window.setContentView(R.layout.unbind_phone_numer);
					TextView yes = (TextView) window.findViewById(R.id.unbind_phone_number_comfirm);
					TextView no = (TextView) window.findViewById(R.id.unbind_phone_number_cancel);
					no.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							alertDialog.dismiss();
						}
					});

					yes.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View view) {
							// TODO Auto-generated method stub
							alertDialog.dismiss();
							cancelBindPhone(String.valueOf(RsSharedUtil.getInt(Binded_Phone_Activity.this,Constants.KEY.WORK_ID)),
									RsSharedUtil.getString(Binded_Phone_Activity.this,Constants.KEY.USER_CODE));
						}
					});

					break;
			}
		}
	};

	private void cancelBindPhone(String workerId, String code){


		JSONObject jsonObject = new JSONObject();


		try {
			jsonObject.put("workerId",workerId);
			jsonObject.put("code",code);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
				Constants.URL.CANCEL_BIND_PHONE, jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.e(TAG,"response: " + response.toString());
				try {
					String res = response.getString("result");
					if (res.equals("checked")) {
						Toast.makeText(Binded_Phone_Activity.this, "手机解除绑定成功", Toast.LENGTH_SHORT).show();
						RsSharedUtil.putString(Binded_Phone_Activity.this, Constants.KEY.USER_PHONE, "");
						final AlertDialog alertDialog2 = new AlertDialog.Builder(Binded_Phone_Activity.this).create();
						alertDialog2.show();
						Window window = alertDialog2.getWindow();
						window.setContentView(R.layout.unbind_phone_number_succeess);
						TextView comfirm = (TextView) window.findViewById(R.id.unbind_phone_number_successs);
						comfirm.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								alertDialog2.dismiss();
								Intent intent = new Intent(Binded_Phone_Activity.this,HomeActivity.class);

								startActivityForResult(intent,Constants.REFRESH_REQUEST);

								finish();
							}
						});


					}else{
						NetResultUtils.badResponse(res,Binded_Phone_Activity.this);
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
