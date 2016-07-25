package com.kai.distribution.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AppStringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kai.distribution.R;
import com.kai.distribution.adapter.Message_listview_adapter;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.entity.Message;
import com.kai.distribution.utils.RsSharedUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@ContentView(R.layout.activity_sys_message)
public class SysMessage_Activity extends Activity
{
	@ViewInject(R.id.system_message_back)
	private Button tv_back;
	@ViewInject(R.id.lv_message)
	private ListView lv_message;
	private static final String TAG = "SysMessage_Activity";
	private List<Message> messages;
	private Message_listview_adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		initView();





		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void initView()
	{
		messages = (List<Message>) getIntent().getSerializableExtra("messages");
		adapter = new Message_listview_adapter(this,R.layout.message_listview_item,messages);
		lv_message.setAdapter(adapter);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


}
