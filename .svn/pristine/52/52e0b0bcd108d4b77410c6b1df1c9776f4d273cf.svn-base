package com.kai.distribution.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kai.distribution.R;

public class Bind_Phone_Activity extends Activity
{
	private TextView bind_phone_return;
	private EditText phone_input;
	private EditText code_input;
	private Button waiting_code;
	private Button bing_phone_comfirm;
	
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
		bind_phone_return=(TextView) findViewById(R.id.bind_phone_return);
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
				
			case R.id.waiting_code:
				
				break;
				
			case R.id.bing_phone_comfirm:
				
				break;
			}
		}
	};
}
