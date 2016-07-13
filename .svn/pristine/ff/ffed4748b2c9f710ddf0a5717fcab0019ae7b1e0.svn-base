package com.kai.distribution.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kai.distribution.R;

public class Register_Next_Activity extends Activity
{
	private ImageView register_next_back;
	private EditText register_next_phone,register_next_code;
	private Button register__next_send_code,register_next_success;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_next);
		
		initView();
	}

	private void initView() {
		register_next_back=(ImageView) findViewById(R.id.register_next_back);
		register_next_phone=(EditText) findViewById(R.id.register_next_phone_Et);
		register_next_code=(EditText) findViewById(R.id.register_next_code_Et);
		register__next_send_code=(Button) findViewById(R.id.register_next_send_code);
		register_next_success=(Button) findViewById(R.id.register_next_success);
	
		register_next_back.setOnClickListener(click);
		register__next_send_code.setOnClickListener(click);
		register_next_success.setOnClickListener(click);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.register_next_back:
				finish();
				break;
				
			case R.id.register_next_send_code:
				
				break;
				
			case R.id.register_next_success:
				
				break;
			}
		}
	};
}
