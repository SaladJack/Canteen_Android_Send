package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kai.distribution.R;

public class Register_Activity extends Activity
{
	private ImageView register_back;
	private EditText register_account, register_name;
	private CheckBox register_check_agreement;
	private TextView agreement;
	private Button register_yes;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		initView();
		
	}

	private void initView() {
		Intent intent_from_agreement =getIntent();
		boolean check_or_no=intent_from_agreement.getBooleanExtra("agree", false);
		register_back=(ImageView) findViewById(R.id.register_back);
		register_account =(EditText) findViewById(R.id.register_account);
		register_name =(EditText) findViewById(R.id.register_name);
		register_check_agreement=(CheckBox) findViewById(R.id.register_check_agreement);
		agreement=(TextView) findViewById(R.id.agreement);
		register_yes=(Button) findViewById(R.id.register_yes);
		
		register_check_agreement.setChecked(check_or_no);
		
		agreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		register_back.setOnClickListener(click);
		agreement.setOnClickListener(click);
		register_yes.setOnClickListener(click);
	}
	
	private OnClickListener click =new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.register_back:
				finish();
				break;
				
			case R.id.agreement:
				intent =new Intent (Register_Activity.this,Register_Agreement_Activity.class);
				startActivity(intent);
				break;
				
			case R.id.register_yes:
				if(register_check_agreement.isChecked())
				{
					intent=new Intent(Register_Activity.this,Register_Next_Activity.class);
					intent.putExtra("account", register_account.getText().toString());
					intent.putExtra("name",register_name.getText().toString());
					startActivity(intent);






				}else
				{
					Toast.makeText(Register_Activity.this,"请同意协议", Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
}
