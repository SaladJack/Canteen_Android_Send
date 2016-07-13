package com.kai.distribution.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kai.distribution.R;

public class Work_Clipboard_Activity extends Activity
{
	private TextView work_clipboard_return,show_calendar_date;
	private TextView clipboard_calendar_year_add,clipboard_calendar_month_add,clipboard_calendar_day_add;
	private TextView clipboard_calendar_year,clipboard_calendar_month,clipboard_calendar_day;
	private TextView clipboard_calendar_year_reduce,clipboard_calendar_month_reduce,clipboard_calendar_day_reduce;
	private Button clipboard_calendar_comfirm,clipboard_calendar_today,clipboard_calendar_cancel;
	
	private LinearLayout clipboard_calendar;
	private static boolean calendar_open=true;
	private String old_year;
	private String old_month;
	private String old_day;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.clipboard);
		
		work_clipboard_return=(TextView) findViewById(R.id.work_clipboard_return);
		show_calendar_date=(TextView) findViewById(R.id.show_calendar_date);
		
		clipboard_calendar=(LinearLayout) findViewById(R.id.clipboard_calendar);
		
		clipboard_calendar_year_add=(TextView) findViewById(R.id.clipboard_calendar_year_add);
		clipboard_calendar_month_add=(TextView) findViewById(R.id.clipboard_calendar_month_add);
		clipboard_calendar_day_add=(TextView) findViewById(R.id.clipboard_calendar_day_add);
		
		clipboard_calendar_year=(TextView) findViewById(R.id.clipboard_calendar_year);
		clipboard_calendar_month=(TextView) findViewById(R.id.clipboard_calendar_month);		
		clipboard_calendar_day=(TextView) findViewById(R.id.clipboard_calendar_day);
		
		clipboard_calendar_year_reduce=(TextView) findViewById(R.id.clipboard_calendar_year_reduce);
		clipboard_calendar_month_reduce=(TextView) findViewById(R.id.clipboard_calendar_month_reduce);
		clipboard_calendar_day_reduce=(TextView) findViewById(R.id.clipboard_calendar_day_reduce);
		
		clipboard_calendar_comfirm=(Button) findViewById(R.id.clipboard_calendar_comfirm);
		clipboard_calendar_today=(Button) findViewById(R.id.clipboard_calendar_today);
		clipboard_calendar_cancel=(Button) findViewById(R.id.clipboard_calendar_cancel);
		
		work_clipboard_return.setOnClickListener(l);
		show_calendar_date.setOnClickListener(l);
		clipboard_calendar_year_add.setOnClickListener(l);
		clipboard_calendar_month_add.setOnClickListener(l);
		clipboard_calendar_day_add.setOnClickListener(l);
		clipboard_calendar_year_reduce.setOnClickListener(l);
		clipboard_calendar_month_reduce.setOnClickListener(l);
		clipboard_calendar_day_reduce.setOnClickListener(l);
		clipboard_calendar_comfirm.setOnClickListener(l);
		clipboard_calendar_today.setOnClickListener(l);
		clipboard_calendar_cancel.setOnClickListener(l);
		
		old_year=clipboard_calendar_year.getText().toString();
		old_month=clipboard_calendar_month.getText().toString();
		old_day=clipboard_calendar_day.getText().toString();
	}
	
	private OnClickListener l=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.work_clipboard_return:
				finish();
				break;
				
			case R.id.show_calendar_date:
				if(calendar_open==true)
				{
					clipboard_calendar.setVisibility(View.VISIBLE);
					calendar_open=false;
				}else if(calendar_open==false)
				{
					clipboard_calendar.setVisibility(View.GONE);
					calendar_open=true;
				}
				break;
				
			case R.id.clipboard_calendar_year_add:
				int current_year_add = Integer.parseInt(clipboard_calendar_year.getText().toString());
				current_year_add++;
				clipboard_calendar_year.setText(Integer.toString(current_year_add));
				clipboard_calendar_month.setText("1");
				clipboard_calendar_day.setText("1");
				break;
				
			case R.id.clipboard_calendar_month_add:
				int current_month_add =Integer.parseInt(clipboard_calendar_month.getText().toString()); 
				current_month_add++;
				int days_month=1;
				clipboard_calendar_day.setText(Integer.toString(days_month));
				if(current_month_add>12){
					current_month_add=1;
					clipboard_calendar_month.setText(Integer.toString(current_month_add));
				}else{
					clipboard_calendar_month.setText(Integer.toString(current_month_add));
				}
				break;
				
			case R.id.clipboard_calendar_day_add:
				int year_to_month_to_day=Integer.parseInt(clipboard_calendar_year.getText().toString());
				int month_to_day=Integer.parseInt(clipboard_calendar_month.getText().toString());
				int current_day_add=Integer.parseInt(clipboard_calendar_day.getText().toString());
				current_day_add++;
				if(year_to_month_to_day%4==0)
				{
					if(month_to_day==2)
					{
						if(current_day_add>29)
						{	
							current_day_add=1;
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
					else if(month_to_day==1||month_to_day==3||month_to_day==5||month_to_day==7||month_to_day==8||month_to_day==10||month_to_day==12)
					{
						if(current_day_add>31)
						{	
							current_day_add=1;
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}
					}else if(month_to_day==4||month_to_day==6||month_to_day==9||month_to_day==11)
					{
						if(current_day_add>30)
						{	
							current_day_add=1;
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
				}else
				{
					if(month_to_day==2)
					{
						if(current_day_add>28)
						{	
							current_day_add=1;
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
					else if(month_to_day==1||month_to_day==3||month_to_day==5||month_to_day==7||month_to_day==8||month_to_day==10||month_to_day==12)
					{
						if(current_day_add>31)
						{	
							current_day_add=1;
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}
					}else if(month_to_day==4||month_to_day==6||month_to_day==9||month_to_day==11)
					{
						if(current_day_add>30)
						{	
							current_day_add=1;
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
				}
				
				
				break;
				
			case R.id.clipboard_calendar_year_reduce:
				int current_year_reduce = Integer.parseInt(clipboard_calendar_year.getText().toString());
				current_year_reduce--;
				clipboard_calendar_year.setText(Integer.toString(current_year_reduce));
				clipboard_calendar_month.setText("1");
				clipboard_calendar_day.setText("1");
				break;
				
			case R.id.clipboard_calendar_month_reduce:
				int current_month_reduce =Integer.parseInt(clipboard_calendar_month.getText().toString()); 
				current_month_reduce--;
				int days_month_reduce=1;
				clipboard_calendar_day.setText(Integer.toString(days_month_reduce));
				if(current_month_reduce<1){
					current_month_reduce=12;
					clipboard_calendar_month.setText(Integer.toString(current_month_reduce));
				}else{
					clipboard_calendar_month.setText(Integer.toString(current_month_reduce));
				}
				break;
				
			case R.id.clipboard_calendar_day_reduce:
				int year_to_month_to_day_reduce=Integer.parseInt(clipboard_calendar_year.getText().toString());
				int month_to_day_reduce=Integer.parseInt(clipboard_calendar_month.getText().toString());
				int current_day_reduce=Integer.parseInt(clipboard_calendar_day.getText().toString());
				current_day_reduce--;
				if(year_to_month_to_day_reduce%4==0)
				{
					if(month_to_day_reduce==2)
					{
						if(current_day_reduce<1)
						{	
							current_day_reduce=29;
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
					else if(month_to_day_reduce==1||month_to_day_reduce==3||month_to_day_reduce==5||month_to_day_reduce==7||month_to_day_reduce==8||month_to_day_reduce==10||month_to_day_reduce==12)
					{
						if(current_day_reduce<1)
						{	
							current_day_reduce=31;
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}else if(month_to_day_reduce==4||month_to_day_reduce==6||month_to_day_reduce==9||month_to_day_reduce==11)
					{
						if(current_day_reduce<1)
						{	
							current_day_reduce=30;
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
				}else
				{
					if(month_to_day_reduce==2)
					{
						if(current_day_reduce>1)
						{	
							current_day_reduce=28;
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
					else if(month_to_day_reduce==1||month_to_day_reduce==3||month_to_day_reduce==5||month_to_day_reduce==7||month_to_day_reduce==8||month_to_day_reduce==10||month_to_day_reduce==12)
					{
						if(current_day_reduce<1)
						{	
							current_day_add=31;
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}else if(month_to_day_reduce==4||month_to_day_reduce==6||month_to_day_reduce==9||month_to_day_reduce==11)
					{
						if(current_day_reduce<1)
						{	
							current_day_add=30;
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							clipboard_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
				}
				break;
				
			case R.id.clipboard_calendar_comfirm:
				show_calendar_date.setText(clipboard_calendar_year.getText().toString()+"-"
										+clipboard_calendar_month.getText().toString()+"-"
										+clipboard_calendar_day.getText().toString());
				break;
				
			case R.id.clipboard_calendar_today:
				Time t=new Time(); 
				t.setToNow(); 
				int year = t.year;
				int month = t.month+1;
				int day = t.monthDay;
				clipboard_calendar_year.setText(Integer.toString(year));
				clipboard_calendar_month.setText(Integer.toString(month));
				clipboard_calendar_day.setText(Integer.toString(day));
				break;
				
			case R.id.clipboard_calendar_cancel:
				clipboard_calendar_year.setText(old_year);
				clipboard_calendar_month.setText(old_month);
				clipboard_calendar_day.setText(old_day);
				break;
			}
		}
	};
}	
