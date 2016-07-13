package com.kai.distribution.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kai.distribution.R;

public class ForgetNumber_NextActivity extends Activity
{
	private ImageButton return_to_ForgetNumberActivity_Btn;
	private EditText new_password_Et,new_password_again_Et;
	private Button forgetnumber_comfirm_Btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgetnumber_tonext);
		initView();
		initOnClick();
	}

	private void initView() {
		// TODO Auto-generated method stub
		return_to_ForgetNumberActivity_Btn=(ImageButton) findViewById(R.id.return_to_ForgetNumberActivity_Btn);
		new_password_Et=(EditText) findViewById(R.id.new_password_Et);
		new_password_again_Et=(EditText) findViewById(R.id.new_password_again_Et);
		forgetnumber_comfirm_Btn=(Button) findViewById(R.id.forgetnumber_comfirm_Btn);
	}
	

	private void initOnClick() {
		// TODO Auto-generated method stub
		return_to_ForgetNumberActivity_Btn.setOnClickListener(click);
		forgetnumber_comfirm_Btn.setOnClickListener(click);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.return_to_ForgetNumberActivity_Btn:
				finish();
				break;
				
			case R.id.forgetnumber_comfirm_Btn:
				
				break;
			
			
			
			}
		}
	};
}
