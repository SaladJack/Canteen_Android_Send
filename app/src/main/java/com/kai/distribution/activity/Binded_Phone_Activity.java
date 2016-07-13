package com.kai.distribution.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kai.distribution.R;


public class Binded_Phone_Activity extends Activity
{
	private TextView binded_phone_back,binded_phone_number;
	private Button unbinded_phone_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.binded_phone);

		binded_phone_back=(TextView) findViewById(R.id.binded_phone_back);
		binded_phone_number=(TextView) findViewById(R.id.binded_phone_number);
		unbinded_phone_number=(Button) findViewById(R.id.unbinded_phone_number);
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
							}
						});
					}
				});

				break;
			}
		}
	};
}
