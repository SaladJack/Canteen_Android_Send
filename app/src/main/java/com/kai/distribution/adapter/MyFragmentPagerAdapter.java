package com.kai.distribution.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kai.distribution.activity.HomeActivity;
import com.kai.distribution.app.Constants;

/**
 * Created by tusm on 16/7/21.
 */
public  class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    private static final String TAG = "MyFragmentPagerAdapter";

    FragmentManager fm;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return HomeActivity.mHomeActivity.frag_list.size();
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG,"getItem: " + position);

        return HomeActivity.mHomeActivity.frag_list.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e(TAG,"instantiateItem");
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        //得到tag，这点很重要
        String fragmentTag = fragment.getTag();

        if (Constants.GLOBAL.UPDATE_FRAGMENT) {
            //如果这个fragment需要更新
            FragmentTransaction ft = fm.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            fragment = HomeActivity.mHomeActivity.frag_list.get(position);
            //添加新fragment时必须用前面获得的tag，这点很重要
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commitAllowingStateLoss();
            //复位更新标志
            Constants.GLOBAL.UPDATE_FRAGMENT= false;
        }


        return fragment;
    }



}
