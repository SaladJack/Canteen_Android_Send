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
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.entity.Register;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

public class Register_Next_Activity extends Activity {
	private ImageView register_next_back;
	private EditText register_password, register_passowd_again;
	private Button register_next_success;
	private EventBus registerBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_next);

		initView();

		registerBus = EventBus.getDefault();
		registerBus.register(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		registerBus.unregister(this);
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
						register(intent.getStringExtra("account"), register_password.getText().toString(), intent.getStringExtra("name"));
					} else {
						Toast.makeText(Register_Next_Activity.this, "两次输入的密码不相同！", Toast.LENGTH_SHORT).show();
						register_passowd_again.setText("");
					}


					break;

			}
		}
	};


    @Subscribe


	private void register(String account, String password, String name) {
		JSONObject jsonObject = new JSONObject();
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

			}


		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
	}
}
