package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kai.distribution.R;

public class ForgetNumberActivity extends Activity 
{
	private EditText forgetnumber_phone_Et,identifynumber_Et;
	private Button send_identifynumber_Btn,forgetnumber_to_next_Btn;
	private ImageButton return_to_mainactivity_Btn;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgetnumber);
		
		initView();
		initOnClick();
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
				
				break;
				
			case R.id.forgetnumber_to_next_Btn:
				intent=new Intent(ForgetNumberActivity.this,ForgetNumber_NextActivity.class);
				startActivity(intent);
				break;
			}
		}
	};
	
}
