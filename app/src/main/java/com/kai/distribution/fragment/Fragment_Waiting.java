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
import com.zxing.activity.CaptureActivity;

public class Fragment_Waiting extends Fragment implements View.OnClickListener {
	private View view;
	private ImageButton scan;
	private static final String TAG = "Fragment_Waiting";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		if (Constants.GLOBAL.HAVE_SCANNED) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					DistrbutingUtils.getDistributedListByHTTP(getActivity().getApplicationContext(), 0);
				}
			}).start();
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
				Intent intent = new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(intent, 0);
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
}
