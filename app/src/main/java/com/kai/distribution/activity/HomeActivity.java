package com.kai.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kai.distribution.R;
import com.kai.distribution.adapter.MyFragmentPagerAdapter;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.fragment.Fragment_AfterScanning;
import com.kai.distribution.fragment.Fragment_Distributied;
import com.kai.distribution.fragment.Fragment_Distributing;
import com.kai.distribution.fragment.Fragment_Mine;
import com.kai.distribution.fragment.Fragment_Waiting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity
{
	private ImageView distributed,distributing,person;
	private ViewPager show_different_fragment;
	private MyFragmentPagerAdapter frag_adapter;

	private LinearLayout ll_distributed,ll_distributing,ll_person;
	private TextView tv_distributed,tv_distributing,tv_person;
	private int selectedTextColor = 0;
	private int unselectedTextColor = 0;


	public List<Fragment> frag_list;
	private Fragment_Distributied fragment_distributied;
	private Fragment_Distributing fragment_distributing;
	private Fragment_Waiting fragment_waiting;
	private Fragment_Mine fragment_mine;
	private Fragment_AfterScanning fragment_afterscanning;

	private static final String TAG = "HomeActivity";

	public static HomeActivity mHomeActivity;



	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(Message msg) {
		Log.e(TAG,"onMessageEvent()");
		Log.e(TAG,"msg.what = " + msg.what);
		switch (msg.what) {

			case Constants.CODE.HAVE_DISTRIBUTING:
				Constants.GLOBAL.UPDATE_FRAGMENT = true;
				Log.e(TAG,"DISTRIBUTING_CHANGED");
				if (mHomeActivity.frag_list.get(1) != mHomeActivity.fragment_distributing) {
					mHomeActivity.frag_list.set(1, mHomeActivity.fragment_distributing);
					mHomeActivity.frag_adapter.notifyDataSetChanged();
				}
				break;
			case Constants.CODE.WAITING:
				Constants.GLOBAL.UPDATE_FRAGMENT = true;
				if (mHomeActivity.frag_list.get(1) != mHomeActivity.fragment_waiting) {
					mHomeActivity.frag_list.set(1, mHomeActivity.fragment_waiting);
					mHomeActivity.frag_adapter.notifyDataSetChanged();
				}
				break;
			case Constants.CODE.SCAN:
				Log.e(TAG,"scan");
				Constants.GLOBAL.UPDATE_FRAGMENT = true;
				if (mHomeActivity.frag_list.get(1) != mHomeActivity.fragment_afterscanning) {
					mHomeActivity.frag_list.set(1, mHomeActivity.fragment_afterscanning);
					mHomeActivity.frag_adapter.notifyDataSetChanged();
				}
				break;
		}
	}



    @Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		EventBus.getDefault().register(this);
		Fresco.initialize(this);
		setContentView(R.layout.home_screen);

		mHomeActivity = this;

		initView();
		initOnClick();
	}

	private void initView() {
		distributed=(ImageView) findViewById(R.id.distributed);
		distributing=(ImageView) findViewById(R.id.distributing);
		person=(ImageView) findViewById(R.id.person);
		show_different_fragment=(ViewPager) findViewById(R.id.show_different_fragment);

		ll_distributed = (LinearLayout) findViewById(R.id.ll_distributed);
		ll_distributing = (LinearLayout) findViewById(R.id.ll_distributing);
		ll_person = (LinearLayout) findViewById(R.id.ll_person);

		tv_distributed = (TextView)findViewById(R.id.tv_distributed);
		tv_distributing = (TextView)findViewById(R.id.tv_distributing);
		tv_person = (TextView)findViewById(R.id.tv_person);


		fragment_distributied=new Fragment_Distributied();
		fragment_distributing=new Fragment_Distributing();
		fragment_waiting=new Fragment_Waiting();
		fragment_mine=new Fragment_Mine();
		fragment_afterscanning = new Fragment_AfterScanning();
		
		frag_list=new ArrayList<Fragment>();
		frag_list.add(fragment_distributied);
		frag_list.add(fragment_waiting);
		frag_list.add(fragment_mine);


		frag_adapter=new MyFragmentPagerAdapter(getSupportFragmentManager());


			

		show_different_fragment.setAdapter(frag_adapter);
		frag_adapter.notifyDataSetChanged();

		
		Intent intent=getIntent();
		int current_fragment=intent.getIntExtra("current_fragment", -1);
		show_different_fragment.setCurrentItem(current_fragment);




		selectedTextColor = getResources().getColor(R.color.button_bottom_tv_selected_color);
		unselectedTextColor = getResources().getColor(R.color.button_bottom_tv_unselected_color);

	}
	
	private void initOnClick() 
	{
		ll_distributed.setOnClickListener(click);
		ll_distributing.setOnClickListener(click);
		ll_person.setOnClickListener(click);
		show_different_fragment.setOnPageChangeListener(viewpager_slide);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			switch(view.getId())
			{			
			case R.id.ll_distributed:
				show_different_fragment.setCurrentItem(0);

				tv_distributed.setTextColor(selectedTextColor);
				tv_distributing.setTextColor(unselectedTextColor);
				tv_person.setTextColor(unselectedTextColor);

				distributed.setImageResource(R.mipmap.yisongda_blue);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_gray);

				break;
				
			case R.id.ll_distributing:
				show_different_fragment.setCurrentItem(1);

				tv_distributed.setTextColor(unselectedTextColor);
				tv_distributing.setTextColor(selectedTextColor);
				tv_person.setTextColor(unselectedTextColor);

				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_blue);
				person.setImageResource(R.mipmap.wode_gray);
				break;
				
			case R.id.ll_person:
				show_different_fragment.setCurrentItem(2);

				tv_distributed.setTextColor(unselectedTextColor);
				tv_distributing.setTextColor(unselectedTextColor);
				tv_person.setTextColor(selectedTextColor);

				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_blue);
				break;
			}
		}



	};



	//滑动效果
	public OnPageChangeListener viewpager_slide=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			if(show_different_fragment.getCurrentItem()==0)
			{
				tv_distributed.setTextColor(selectedTextColor);
				tv_distributing.setTextColor(unselectedTextColor);
				tv_person.setTextColor(unselectedTextColor);

				distributed.setImageResource(R.mipmap.yisongda_blue);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_gray);
			}else if(show_different_fragment.getCurrentItem()==1)
			{

				tv_distributed.setTextColor(unselectedTextColor);
				tv_distributing.setTextColor(selectedTextColor);
				tv_person.setTextColor(unselectedTextColor);

				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_blue);
				person.setImageResource(R.mipmap.wode_gray);
			}else if(show_different_fragment.getCurrentItem()==2)
			{

				tv_distributed.setTextColor(unselectedTextColor);
				tv_distributing.setTextColor(selectedTextColor);
				tv_person.setTextColor(unselectedTextColor);


				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_blue);
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e(TAG,"onActivityResult()");
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
			case Constants.REFRESH_REQUEST:
				fragment_mine.initView();
				break;
			case RESULT_OK:
				Log.e(TAG,"二维码扫描成功");
				String res = data.getExtras().getString("result");
				if (TextUtils.isEmpty(res))
					return;
				if (res.equals("success")) {
					Log.e(TAG, "success");
					Constants.GLOBAL.HAVE_SCANNED = true;
					Log.e("Click","click : "+Constants.GLOBAL.HAVE_SCANNED );
					Message msg = Message.obtain();
					msg.what = Constants.CODE.SCAN;
					Log.e(TAG,"toAfterScanning");
					EventBus.getDefault().post(msg);
				} else {
					Toast.makeText(HomeActivity.this, "二维码数据错误", Toast.LENGTH_SHORT).show();
				}
		}
	}

		//设置有空
	private void changeStatu(String code, int workerId){
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("code",code);
			jsonObject.put("workerId",workerId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL.CHANGE_STATU, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						String res = null ;
						try {
							res = response.getString("result");
							if (res.equals("success")){

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		MyApplication.getRequestQueue().add(jsonObjectRequest);
	}


	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
		//数据重置
		Constants.GLOBAL.UPDATE_FRAGMENT = false;
		Constants.GLOBAL.HAVE_SCANNED = false;
		frag_list.clear();
		Log.e(TAG,"onDestroy");
	}
}


