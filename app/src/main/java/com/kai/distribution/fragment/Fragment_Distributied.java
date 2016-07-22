package com.kai.distribution.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.header.MaterialHeader;
import com.kai.distribution.R;
import com.kai.distribution.adapter.Distributed_listview_adapter;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.entity.Distributed;
import com.kai.distribution.utils.JsonToBean;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Distributied extends Fragment
{

	private PtrFrameLayout mPtrFrameLayout;
	private Handler handler = new Handler();

	private View view;
	private Spinner distributed_area,distributed_dorm,distributed_time;
	private ListView distributed_show_list;
	private Distributed_listview_adapter listview_adapter;
	
	private List<String> area_spinner_content;
	private List<String> dorm_spinner_content;
	private List<String> time_spinner_content;

	private final String[] area_spinner_text={"全部","1区","2区","3区","4区","5区"};
	private final String[] dorm_spinner_text={"全部","宿舍","其他"};
	private final String[] time_spinner_text={"11:30~11：45","12:00~12:15"};
	
	private ArrayAdapter<String> area_spinner_adapter;
	private ArrayAdapter<String> dorm_spinner_adapter;
	private ArrayAdapter<String> time_spinner_adapter;
	
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
	private int pageIndex = 1;
	private int sendAreId = 0;
	private int buildingtype = 0 ;
	private long sendTimeBegin = 0l;
	private long sendTimeEnd = 0l;
	private List<Distributed> newDatas;
	private JSONArray unparsedNewDatas;
	private List<Distributed> ditributedsList = new ArrayList<>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view=inflater.inflate(R.layout.fragment_distributed, container,false);
		initView();
		initData();
		return view;
	}

	private void initView() {
		distributed_area=(Spinner) view.findViewById(R.id.distributed_area);
		distributed_dorm=(Spinner) view.findViewById(R.id.distributed_dorm);
		distributed_time = (Spinner)view.findViewById(R.id.distributed_time);


		mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.distributed_list_view);

		// header
		final MaterialHeader header = new MaterialHeader(getContext());
		int[] colors = getResources().getIntArray(R.array.google_colors);
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, 15, 0, 15);
		header.setPtrFrameLayout(mPtrFrameLayout);
		mPtrFrameLayout.setHeaderView(header);
		mPtrFrameLayout.addPtrUIHandler(header);

		//TODO 配送区域Spinner
		area_spinner_content = new ArrayList<String>();
		for (int i = 0; i < area_spinner_text.length; i++) {
			area_spinner_content.add(area_spinner_text[i]);
		}
		area_spinner_adapter = new ArrayAdapter(getActivity(),R.layout.show_distributed_spinner_text,R.id.spinner_tv,area_spinner_content);
		area_spinner_adapter.setDropDownViewResource(R.layout.spinner_item_layout);
		
		distributed_area.setAdapter(area_spinner_adapter);
		
		//TODO 配送楼栋Spinner
		dorm_spinner_content = new ArrayList<String>();
		for (int i = 0; i < dorm_spinner_text.length; i++) {
			dorm_spinner_content.add(dorm_spinner_text[i]);
		}
		dorm_spinner_adapter = new ArrayAdapter(getActivity(), R.layout.show_distributed_spinner_text,R.id.spinner_tv,dorm_spinner_content);
		dorm_spinner_adapter.setDropDownViewResource(R.layout.spinner_item_layout);

		distributed_dorm.setAdapter(dorm_spinner_adapter);

		//TODO 送达时间Spinner
		time_spinner_content = new ArrayList<String>();
		for (int i = 0; i < time_spinner_text.length; i++) {
			time_spinner_content.add(time_spinner_text[i]);
		}
		time_spinner_adapter = new ArrayAdapter(getActivity(), R.layout.show_time_spinner_text,R.id.spinner_tv,time_spinner_content);
		time_spinner_adapter.setDropDownViewResource(R.layout.spinner_item_layout);

		distributed_time.setAdapter(time_spinner_adapter);


		distributed_show_list=(ListView) view.findViewById(R.id.distributed_show_list);
		listview_adapter = new Distributed_listview_adapter(getActivity(), R.layout.show_time_spinner_text,ditributedsList);
		distributed_show_list.setAdapter(listview_adapter);
		
		//设置时间选择
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


	private void initData(){
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.e("debug","postDelayed");
				mPtrFrameLayout.autoRefresh(true);
			}
		}, 1);

//		mPtrFrameLayout.setLoadMoreEnable(false);


		mPtrFrameLayout.setPtrHandler(new PtrHandler() {


			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return true;
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						//添加刷新事件
						Log.e("debug","setPtrHandler--onRefreshBegin");
						pageIndex = 1;
						Log.e("debug","onRefreshBegin访问网络");
						getDistributedListByHTTP(true);

						mPtrFrameLayout.refreshComplete();
					}
				}, 1000);
			}
		});

//		mPtrFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//
//			@Override
//			public void loadMore() {
//				handler.postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						//添加加载事件
//						Log.e("debug","setPtrHandler--onRefreshBegin");
//						pageIndex++;
//						Log.e("debug","onRefreshBegin访问网络");
//						getDistributedListByHTTP(false);
//						mPtrFrameLayout.loadMoreComplete(true);
//						Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
//					}
//				}, 1000);
//			}
//		});
	}



	@Override
	public void onStart() {
		super.onStart();
		if (listview_adapter != null) {
			listview_adapter.notifyDataSetChanged();
		}
	}


/*
  url：http:// milk345.imwork.net:13607/Canteen/worker/showSendedOrders
方法：POST
入参：
 {
"code":不含时间戳的令牌（string）
"workerId":配送员Id（int）
"pageIndex":页码(int)        ？一页有多少个数据？
"date":日期,当天凌晨毫秒数(long)
"sendAreaId":配送区域Id(int)    (0:全部)				  **********
"buildingType":建筑类型(int) (0:全部；1：宿舍；5：其他)   **********
"sendTimeBegin":送达开始时间(long)
"sendTimeEnd":送达结束时间(long)
}

 */

	private void getDistributedListByHTTP(final boolean clearDataFlag){

		JSONObject jsonObject = new JSONObject();
		String url = Constants.URL.DISTRIBUTED_URL;

//		Student student = getStudent();
		try {
			jsonObject.put("code", RsSharedUtil.getString(getContext(),"code"));
			jsonObject.put("workerId", RsSharedUtil.getInt(getActivity(),Constants.KEY.WORK_ID));
			jsonObject.put("pageIndex",pageIndex);
			jsonObject.put("date", System.currentTimeMillis());
			jsonObject.put("sendAreaId", sendAreId);
			jsonObject.put("buildingType", buildingtype);
			jsonObject.put("sendTimeBegin",sendTimeBegin );
			jsonObject.put("sendTimeEnd", sendTimeEnd);


		} catch (JSONException e) {
			e.printStackTrace();
		}


		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST,  url, jsonObject,
				new Response.Listener<org.json.JSONObject>() {
					@Override
					public void onResponse(org.json.JSONObject response) {
						try {
							Log.e("searchFragmentResponse",response.toString());
//							totalPage = response.getInt("totalPage");
							unparsedNewDatas = response.getJSONArray("array");
							manageData(clearDataFlag);//处理数据
							Log.i("onResponse","success");
						} catch (Exception e1) {
							e1.printStackTrace();
							try {
								String result = response.getString("result");
//								onFailResponse(result);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				try {
					Toast.makeText(getActivity(), "网络不给力", Toast.LENGTH_SHORT).show();
					Log.i("searchFragmentError",error.toString());
				} catch (Exception e) {
				}
			}

		}

		);
		MyApplication.getRequestQueue().add(jsonObjectRequest);
	}

	private void manageData(boolean clearDataFlag){
		if (newDatas != null){
			newDatas.clear();
		}else {
			newDatas = new ArrayList<>();
		}

		if (unparsedNewDatas == null || unparsedNewDatas.length()==0){
			Toast.makeText(getActivity(),"没有更多的餐厅了",Toast.LENGTH_SHORT).show();
			return;
		} else {

					/*TODO: 16/7/18
					  解析成Bean
					  */


			newDatas = JsonToBean.getDistributeds(unparsedNewDatas.toString());

			unparsedNewDatas = null;

			if(clearDataFlag)
				ditributedsList.clear();
			ditributedsList.addAll(newDatas);
			Log.e("searchFragment","canteensList.size() = "+ ditributedsList.size());
			listview_adapter.notifyDataSetChanged();

		}

	}
}
