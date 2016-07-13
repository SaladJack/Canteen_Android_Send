package com.kai.distribution.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kai.distribution.R;

public class Fragment_Waiting extends Fragment
{
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view=inflater.inflate(R.layout.wating_fragment, container,false);
		return view;
	}
}
