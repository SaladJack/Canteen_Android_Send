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

public class Changed_Password_Activity extends Activity
{
	private TextView password_return;
	private EditText old_password;
	private EditText new_password_1;
	private EditText new_password_2;
	private Button new_password_comfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_change_password);
		
		initView();
		initClick();
	}

	private void initView() {
		password_return=(TextView) findViewById(R.id.password_return);
		old_password=(EditText) findViewById(R.id.old_password);
		new_password_1=(EditText) findViewById(R.id.new_password_1);
		new_password_2=(EditText) findViewById(R.id.new_password_2);
		new_password_comfirm=(Button) findViewById(R.id.new_password_comfirm);
	}
	
	private void initClick() {
		password_return.setOnClickListener(click);
		new_password_comfirm.setOnClickListener(click);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.password_return:
				finish();
				break;
			
			case R.id.new_password_comfirm:
				
				break;
			}
		}
	};
}
