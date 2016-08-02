package com.kai.distribution.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kai.distribution.R;
import com.kai.distribution.activity.HomeActivity;
import com.kai.distribution.app.Constants;
import com.kai.distribution.utils.DistrbutingUtils;
import com.orhanobut.logger.Logger;
import com.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Waiting extends Fragment implements View.OnClickListener {
	private View view;
	private ImageButton scan;
	private static final String TAG = "Fragment_Waiting";
	private Timer timer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		if (Constants.GLOBAL.HAVE_SCANNED) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					DistrbutingUtils.getDistributedListByHTTP(getActivity().getApplicationContext(), 0);
				}
			},0,60000);//一分钟请求一次网络

		}

		view = inflater.inflate(R.layout.fragment_waiting, container, false);
		initView();
		return view;
	}

	private void initView() {
		scan = (ImageButton) view.findViewById(R.id.fragment_waiting_scan);
		scan.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fragment_waiting_scan:
//				Intent intent = new Intent(getActivity(), CaptureActivity.class);
//				startActivityForResult(intent, 0);

				Message msg = Message.obtain();
				msg.what = Constants.CODE.HAVE_DISTRIBUTING;
				EventBus.getDefault().post(msg);
				Logger.e("EventBus  post");
				break;
		}
	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (resultCode) {
//			case Activity.RESULT_OK:
//				String res = data.getExtras().getString("result");
//				if (TextUtils.isEmpty(res))
//					return;
//				if (res.equals("success")) {
//					Log.e(TAG, "success");
//					Message msg = Message.obtain();
//					msg.what = Constants.CODE.SCAN;
//					HomeActivity.sHandler.sendMessage(msg);
//					Constants.GLOBAL.HAVE_SCANNED = true;
//				} else {
//					Toast.makeText(getActivity(), "二维码数据错误", Toast.LENGTH_SHORT).show();
//				}
//		}
//	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer!=null) {
			timer.cancel();
			timer = null;
		}
	}
}
