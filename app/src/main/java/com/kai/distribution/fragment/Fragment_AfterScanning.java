package com.kai.distribution.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kai.distribution.R;

/**
 * Created by tusm on 16/7/20.
 */
public class Fragment_AfterScanning extends Fragment {
    private View view;

    private static final String TAG = "Fragment_AfterScanning";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG,TAG + ":onCreateView");
        view=inflater.inflate(R.layout.wating_fragment, container,false);
        return view;
    }
}
