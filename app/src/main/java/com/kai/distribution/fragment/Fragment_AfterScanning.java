package com.kai.distribution.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kai.distribution.R;
import com.kai.distribution.activity.HomeActivity;
import com.kai.distribution.app.Constants;
import com.kai.distribution.utils.DistrbutingUtils;

/**
 * Created by tusm on 16/7/20.
 */
public class Fragment_AfterScanning extends Fragment {
    private View view;

    private static final String TAG = "Fragment_AfterScanning";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    DistrbutingUtils.getDistributedListByHTTP(HomeActivity.mHomeActivity, 0);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
        view=inflater.inflate(R.layout.fragment_afterscanning, container,false);
        return view;
    }
}
