package com.kai.distribution.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kai.distribution.R;

public class Version_Activity extends Activity
{
	private ImageButton set_up_version_return;
	private Button update_version;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_up_version);
		
		set_up_version_return=(ImageButton) findViewById(R.id.set_up_version_return);
		update_version=(Button) findViewById(R.id.update_version);
		
		set_up_version_return.setOnClickListener(click);
		update_version.setOnClickListener(click);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.set_up_version_return:
				finish();
				break;
				
			case R.id.update_version:
				
				break;
			}
		}
	};
}
