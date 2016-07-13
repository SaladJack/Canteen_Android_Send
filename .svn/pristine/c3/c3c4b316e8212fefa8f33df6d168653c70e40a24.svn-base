package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kai.distribution.R;

public class Register_Agreement_Activity extends Activity
{
	private TextView agreement_back;
	private ScrollView register_agreement;
	private Button agreement_agree;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_user_agreement);
		
		initView();
		
	}
	
	private void initView() {
		agreement_back=(TextView) findViewById(R.id.agreement_agree);
		register_agreement=(ScrollView) findViewById(R.id.register_agreement);
		agreement_agree=(Button) findViewById(R.id.agreement_agree);
		
		agreement_back.setOnClickListener(click);
		agreement_agree.setOnClickListener(click);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.agreement_back:
				finish();
				break;

				
			case R.id.agreement_agree:
				Intent intent=new Intent(Register_Agreement_Activity.this,Register_Activity.class);
				intent.putExtra("agree", true);
				startActivity(intent);
				Register_Agreement_Activity.this.finish();
				break;
			}
		}
	};
}
