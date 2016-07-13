package com.kai.distribution.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kai.distribution.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Distributied extends Fragment
{
	private View view;
	private Spinner distributed_area,distributed_dorm;
	private ListView distributed_show_list;
	private ListView_Adapter listview_adapter;
	
	private List<String> area_spinner_content;
	private List<String> dorm_spinner_content;

	private final String[] area_spinner_text={"全部","1区","2区","3区","4区","5区"};
	private final String[] dorm_spinner_text={"全部","宿舍地址","其他地址"};
	
	private ArrayAdapter<String> area_spinner_adapter;
	private ArrayAdapter<String> dorm_spinner_adapter;
	
	private TextView show_distributed_calendar;
	private LinearLayout distributed_calendar;
	private TextView distributed_calendar_year_add,distributed_calendar_month_add,distributed_calendar_day_add;
	private TextView distributed_calendar_year,distributed_calendar_month,distributed_calendar_day;
	private TextView distributed_calendar_year_reduce,distributed_calendar_month_reduce,distributed_calendar_day_reduce;
	private Button distributed_calendar_comfrim,distributed_calendar_today,distributed_calendar_cancel;
	
	private static boolean calendar_open=true;
	private String old_year;
	private String old_month;
	private String old_day;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view=inflater.inflate(R.layout.fragment_distributed, container,false);
		initView();
		return view;
	}

	private void initView() {
		distributed_area=(Spinner) view.findViewById(R.id.distributed_area);
		distributed_dorm=(Spinner) view.findViewById(R.id.distributed_dorm);
		
		//����
		area_spinner_content = new ArrayList<String>();
		for (int i = 0; i < area_spinner_text.length; i++) {
			area_spinner_content.add(area_spinner_text[i]);
		}
		area_spinner_adapter = new ArrayAdapter(getActivity(),
				R.layout.show_distributed_spinner_text,area_spinner_content);
		area_spinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		distributed_area.setAdapter(area_spinner_adapter);
		

		dorm_spinner_content = new ArrayList<String>();
		for (int i = 0; i < dorm_spinner_text.length; i++) {
			dorm_spinner_content.add(dorm_spinner_text[i]);
		}
		dorm_spinner_adapter = new ArrayAdapter(getActivity(),
				R.layout.show_distributed_spinner_text,dorm_spinner_content);
		dorm_spinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		distributed_dorm.setAdapter(dorm_spinner_adapter);
		
		distributed_show_list=(ListView) view.findViewById(R.id.distributed_show_list);
		listview_adapter = new ListView_Adapter(getActivity(), 8);
		distributed_show_list.setAdapter(listview_adapter);
		
		//���ʹ������
		show_distributed_calendar=(TextView) view.findViewById(R.id.show_distributed_calendar);
		distributed_calendar=(LinearLayout) view.findViewById(R.id.distributed_calendar);
		
		distributed_calendar_year_add=(TextView) view.findViewById(R.id.distributed_calendar_year_add);
		distributed_calendar_month_add=(TextView) view.findViewById(R.id.distributed_calendar_month_add);
		distributed_calendar_day_add=(TextView) view.findViewById(R.id.distributed_calendar_day_add);
	
		distributed_calendar_year=(TextView)view.findViewById(R.id.distributed_calendar_year);
		distributed_calendar_month=(TextView)view.findViewById(R.id.distributed_calendar_month);
		distributed_calendar_day=(TextView)view.findViewById(R.id.distributed_calendar_day);
	
		distributed_calendar_year_reduce=(TextView) view.findViewById(R.id.distributed_calendar_year_reduce);
		distributed_calendar_month_reduce=(TextView) view.findViewById(R.id.distributed_calendar_month_reduce);
		distributed_calendar_day_reduce=(TextView) view.findViewById(R.id.distributed_calendar_day_reduce);
	
		distributed_calendar_comfrim=(Button) view.findViewById(R.id.distributed_calendar_comfrim);
		distributed_calendar_today=(Button) view.findViewById(R.id.distributed_calendar_today);
		distributed_calendar_cancel=(Button) view.findViewById(R.id.distributed_calendar_cancel);
	
		show_distributed_calendar.setOnClickListener(click);
		distributed_calendar_year_add.setOnClickListener(click);
		distributed_calendar_month_add.setOnClickListener(click);
		distributed_calendar_day_add.setOnClickListener(click);
		distributed_calendar_year_reduce.setOnClickListener(click);
		distributed_calendar_month_reduce.setOnClickListener(click);
		distributed_calendar_day_reduce.setOnClickListener(click);
		distributed_calendar_comfrim.setOnClickListener(click);
		distributed_calendar_today.setOnClickListener(click);
		distributed_calendar_cancel.setOnClickListener(click);
		
		old_year=distributed_calendar_year.getText().toString();
		old_month=distributed_calendar_month.getText().toString();
		old_day=distributed_calendar_day.getText().toString();
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.show_distributed_calendar:
				if(calendar_open==true)
				{
					distributed_calendar.setVisibility(View.VISIBLE);
					calendar_open=false;
				}else if(calendar_open==false)
				{
					distributed_calendar.setVisibility(View.GONE);
					calendar_open=true;
				}
				break;
				
			case R.id.distributed_calendar_year_add:
				int current_year_add = Integer.parseInt(distributed_calendar_year.getText().toString());
				current_year_add++;
				distributed_calendar_year.setText(Integer.toString(current_year_add));
				distributed_calendar_month.setText("1");
				distributed_calendar_day.setText("1");
				break;
				
			case R.id.distributed_calendar_month_add:
				int current_month_add =Integer.parseInt(distributed_calendar_month.getText().toString()); 
				current_month_add++;
				int days_month=1;
				distributed_calendar_day.setText(Integer.toString(days_month));
				if(current_month_add>12){
					current_month_add=1;
					distributed_calendar_month.setText(Integer.toString(current_month_add));
				}else{
					distributed_calendar_month.setText(Integer.toString(current_month_add));
				}
				break;
				
			case R.id.distributed_calendar_day_add:
				int year_to_month_to_day=Integer.parseInt(distributed_calendar_year.getText().toString());
				int month_to_day=Integer.parseInt(distributed_calendar_month.getText().toString());
				int current_day_add=Integer.parseInt(distributed_calendar_day.getText().toString());
				current_day_add++;
				if(year_to_month_to_day%4==0)
				{
					if(month_to_day==2)
					{
						if(current_day_add>29)
						{	
							current_day_add=1;
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
					else if(month_to_day==1||month_to_day==3||month_to_day==5||month_to_day==7||month_to_day==8||month_to_day==10||month_to_day==12)
					{
						if(current_day_add>31)
						{	
							current_day_add=1;
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}
					}else if(month_to_day==4||month_to_day==6||month_to_day==9||month_to_day==11)
					{
						if(current_day_add>30)
						{	
							current_day_add=1;
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
				}else
				{
					if(month_to_day==2)
					{
						if(current_day_add>28)
						{	
							current_day_add=1;
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
					else if(month_to_day==1||month_to_day==3||month_to_day==5||month_to_day==7||month_to_day==8||month_to_day==10||month_to_day==12)
					{
						if(current_day_add>31)
						{	
							current_day_add=1;
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}
					}else if(month_to_day==4||month_to_day==6||month_to_day==9||month_to_day==11)
					{
						if(current_day_add>30)
						{	
							current_day_add=1;
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_add));
						}
					}
				}
				
				
				break;
				
			case R.id.distributed_calendar_year_reduce:
				int current_year_reduce = Integer.parseInt(distributed_calendar_year.getText().toString());
				current_year_reduce--;
				distributed_calendar_year.setText(Integer.toString(current_year_reduce));
				distributed_calendar_month.setText("1");
				distributed_calendar_day.setText("1");
				break;
				
			case R.id.distributed_calendar_month_reduce:
				int current_month_reduce =Integer.parseInt(distributed_calendar_month.getText().toString()); 
				current_month_reduce--;
				int days_month_reduce=1;
				distributed_calendar_day.setText(Integer.toString(days_month_reduce));
				if(current_month_reduce<1){
					current_month_reduce=12;
					distributed_calendar_month.setText(Integer.toString(current_month_reduce));
				}else{
					distributed_calendar_month.setText(Integer.toString(current_month_reduce));
				}
				break;
				
			case R.id.distributed_calendar_day_reduce:
				int year_to_month_to_day_reduce=Integer.parseInt(distributed_calendar_year.getText().toString());
				int month_to_day_reduce=Integer.parseInt(distributed_calendar_month.getText().toString());
				int current_day_reduce=Integer.parseInt(distributed_calendar_day.getText().toString());
				current_day_reduce--;
				if(year_to_month_to_day_reduce%4==0)
				{
					if(month_to_day_reduce==2)
					{
						if(current_day_reduce<1)
						{	
							current_day_reduce=29;
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
					else if(month_to_day_reduce==1||month_to_day_reduce==3||month_to_day_reduce==5||month_to_day_reduce==7||month_to_day_reduce==8||month_to_day_reduce==10||month_to_day_reduce==12)
					{
						if(current_day_reduce<1)
						{	
							current_day_reduce=31;
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}else if(month_to_day_reduce==4||month_to_day_reduce==6||month_to_day_reduce==9||month_to_day_reduce==11)
					{
						if(current_day_reduce<1)
						{	
							current_day_reduce=30;
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
				}else
				{
					if(month_to_day_reduce==2)
					{
						if(current_day_reduce>1)
						{	
							current_day_reduce=28;
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
					else if(month_to_day_reduce==1||month_to_day_reduce==3||month_to_day_reduce==5||month_to_day_reduce==7||month_to_day_reduce==8||month_to_day_reduce==10||month_to_day_reduce==12)
					{
						if(current_day_reduce<1)
						{	
							current_day_add=31;
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}else if(month_to_day_reduce==4||month_to_day_reduce==6||month_to_day_reduce==9||month_to_day_reduce==11)
					{
						if(current_day_reduce<1)
						{	
							current_day_add=30;
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}else
						{
							distributed_calendar_day.setText(Integer.toString(current_day_reduce));
						}
					}
				}
				break;
				
			case R.id.distributed_calendar_comfrim:
				show_distributed_calendar.setText(distributed_calendar_year.getText().toString()+"-"
										+distributed_calendar_month.getText().toString()+"-"
										+distributed_calendar_day.getText().toString());
				break;
				
			case R.id.distributed_calendar_today:
				Time t=new Time(); 
				t.setToNow(); 
				int year = t.year;
				int month = t.month+1;
				int day = t.monthDay;
				distributed_calendar_year.setText(Integer.toString(year));
				distributed_calendar_month.setText(Integer.toString(month));
				distributed_calendar_day.setText(Integer.toString(day));
				break;
				
			case R.id.distributed_calendar_cancel:
				distributed_calendar_year.setText(old_year);
				distributed_calendar_month.setText(old_month);
				distributed_calendar_day.setText(old_day);
				break;
			}
		}
	};
	
	public class ListView_Adapter extends BaseAdapter {
		private Context context;
		private int i;

		public ListView_Adapter(Context context, int i)

		{
			this.context = context;
			this.i = i;
		}

		@Override
		public int getCount() {
			return i;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View view, ViewGroup viewgroup) {
			final ViewHolder holder;
			if (view == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				view = inflater.inflate(R.layout.distributed_takeoutfood,
						viewgroup, false);
				holder = new ViewHolder();
				holder.distributed_person_name = (TextView) view.findViewById(R.id.distributed_person_name);
				holder.distributed_which_area = (TextView) view.findViewById(R.id.distributed_which_area);
				holder.distributed_which_time = (TextView) view.findViewById(R.id.distributed_which_time);
				
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			
			return view;
		}

	}

	static class ViewHolder {
		public TextView distributed_person_name;
		public TextView distributed_which_area;
		public TextView distributed_which_time;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (listview_adapter != null) {
			listview_adapter.notifyDataSetChanged();
		}
	}
	
}
