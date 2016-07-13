package com.kai.distribution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.kai.distribution.R;
import com.kai.distribution.fragment.Fragment_Distributied;
import com.kai.distribution.fragment.Fragment_Distributing;
import com.kai.distribution.fragment.Fragment_Mine;
import com.kai.distribution.fragment.Fragment_Waiting;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity
{
	private ImageView distributed,distributing,person;
	private ViewPager show_different_fragment;
	private FragmentPagerAdapter frag_adapter;
	private List<Fragment> frag_list;

	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_screen);
		
		initView();
		initOnClick();
	}

	private void initView() {
		distributed=(ImageView) findViewById(R.id.distributed);
		distributing=(ImageView) findViewById(R.id.distributing);
		person=(ImageView) findViewById(R.id.person);
		show_different_fragment=(ViewPager) findViewById(R.id.show_different_fragment);

		Fragment_Distributied fragment_distributied=new Fragment_Distributied();
		Fragment_Distributing fragment_distributing=new Fragment_Distributing();
		Fragment_Waiting fragment_waiting=new Fragment_Waiting();
		Fragment_Mine fragment_mine=new Fragment_Mine();
		
		frag_list=new ArrayList<Fragment>();
		frag_list.add(fragment_distributied);
		frag_list.add(fragment_distributing);
		frag_list.add(fragment_mine);
		
		frag_adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return frag_list.size();
			}
			
			@Override
			public Fragment getItem(int position) {
				return frag_list.get(position);
			}
		};
		show_different_fragment.setAdapter(frag_adapter);
		
		Intent intent=getIntent();
		int current_fragment=intent.getIntExtra("current_fragment", -1);
		show_different_fragment.setCurrentItem(current_fragment);
	}
	
	private void initOnClick() 
	{
		distributed.setOnClickListener(click);
		distributing.setOnClickListener(click);
		person.setOnClickListener(click);
		show_different_fragment.setOnPageChangeListener(viewpager_slide);
	}
	
	private OnClickListener click=new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			switch(view.getId())
			{			
			case R.id.distributed:
				show_different_fragment.setCurrentItem(0);
				distributed.setImageResource(R.mipmap.yisongda_blue);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_gray);
				break;
				
			case R.id.distributing:
				show_different_fragment.setCurrentItem(1);
				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_blue);
				person.setImageResource(R.mipmap.wode_gray);
				break;
				
			case R.id.person:
				show_different_fragment.setCurrentItem(2);
				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_blue);
				break;
			}
		}
	};
	
	public OnPageChangeListener viewpager_slide=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			if(show_different_fragment.getCurrentItem()==0)
			{
				distributed.setImageResource(R.mipmap.yisongda_blue);
				distributing.setImageResource(R.mipmap.daisongcan_gray);
				person.setImageResource(R.mipmap.wode_gray);
			}else if(show_different_fragment.getCurrentItem()==1)
			{
				distributed.setImageResource(R.mipmap.yisongda_gray);
				distributing.setImageResource(R.mipmap.daisongcan_blue);
				person.setImageResource(R.mipmap.wode_gray);
			}else if(show_different_fragment.getCurrentItem()==2)
			{
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
}
