package com.kai.distribution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kai.distribution.R;
import com.kai.distribution.activity.Bind_Phone_Activity;
import com.kai.distribution.activity.Binded_Phone_Activity;
import com.kai.distribution.activity.Changed_Password_Activity;
import com.kai.distribution.activity.LoginActivity;
import com.kai.distribution.activity.Setup_Activity;
import com.kai.distribution.activity.SysMessage_Activity;
import com.kai.distribution.activity.Work_Clipboard_Activity;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.entity.Message;
import com.kai.distribution.utils.JsonToBean;
import com.kai.distribution.utils.RsSharedUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;


public class Fragment_Mine extends Fragment
{
	@ViewInject(R.id.tv_name)
	private TextView tv_name;
	@ViewInject(R.id.tv_phone)
	private TextView tv_phone;
	@ViewInject(R.id.iv_logo)
	private ImageView iv_logo;
	@ViewInject(R.id.ll_user)
	private LinearLayout ll_user;
	@ViewInject(R.id.phone_number)
	private TextView phone_number;
	@ViewInject(R.id.system_message_num)
	private TextView system_message_num;
	private List<Message> messages;
	private int messageNum;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_mine, container,false);
		ViewUtils.inject(this, view);
		initView();

		showMessage(1,RsSharedUtil.getString(getActivity(),Constants.KEY.USER_CODE),
				    RsSharedUtil.getInt(getActivity(),Constants.KEY.WORK_ID));

		return view;
	}

	@Override
	public void onStart() {
		if (TextUtils.isEmpty(RsSharedUtil.getString(getActivity(),Constants.KEY.USER_PHONE))){
			phone_number.setText("未绑定手机号");
		}
		super.onStart();
	}

	//返回该Fragment需要刷新界面 public不能更改
	public void initView() {
		if (!RsSharedUtil.getString(getActivity(), Constants.KEY.USER_CODE).equals("") ||
				!RsSharedUtil.getString(getActivity(), Constants.KEY.USER_CODE).equalsIgnoreCase("null")) {
			ll_user.setVisibility(View.VISIBLE);

			tv_name.setText(RsSharedUtil.getString(getActivity(), Constants.KEY.USER_NAME));
			tv_phone.setText(RsSharedUtil.getString(getActivity(), Constants.KEY.USER_PHONE));
//			Picasso.with(getActivity())
//					.load(RsSharedUtil.getString(getActivity(), Constants.KEY.USER_PHOTO))
//					.error(R.drawable.img_load)
//					.into(iv_logo);
			if (!TextUtils.isEmpty(RsSharedUtil.getString(getActivity(), Constants.KEY.USER_PHONE))) {
				phone_number.setText(RsSharedUtil.getString(getActivity(), Constants.KEY.USER_PHONE));
			}
		}else {

			ll_user.setVisibility(View.GONE);
		}

	}


	@OnClick({R.id.ll_work,R.id.ll_password,R.id.ll_bind,R.id.ll_set,R.id.ll_system})
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent  intent=null;
		if (!RsSharedUtil.getString(getActivity(), Constants.KEY.USER_CODE).equals("")||
				!RsSharedUtil.getString(getActivity(), Constants.KEY.USER_CODE).equalsIgnoreCase("null"))
		{
			switch(view.getId())
			{
				//工作汇总
				case R.id.ll_work:
					intent=new Intent(getActivity(),Work_Clipboard_Activity.class);
					startActivity(intent);
					break;
				//修改密码
				case R.id.ll_password:
					intent=new Intent(getActivity(),Changed_Password_Activity.class);
					startActivity(intent);
					break;
				//绑定手机
				case R.id.ll_bind:
					if (TextUtils.isEmpty(RsSharedUtil.getString(getActivity(),Constants.KEY.USER_PHONE))) {
						intent=new Intent(getActivity(),Bind_Phone_Activity.class);
						startActivity(intent);
						break;
					}
					else{
						intent = new Intent(getActivity(), Binded_Phone_Activity.class);
						startActivity(intent);
						break;
					}
					//设置
				case R.id.ll_set:
					intent=new Intent(getActivity(),Setup_Activity.class);
					startActivity(intent);
					break;
				//系统消息
				case R.id.ll_system:
					intent=new Intent(getActivity(),SysMessage_Activity.class);
					intent.putExtra("messages", (Serializable) messages);
					startActivity(intent);
					break;


			}
		}
		else {
			intent=new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
		}
	}

	private void showMessage(int sign,String code, int workerId){
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("sign", sign);
			jsonObject.put("code",code);
			jsonObject.put("workerId",workerId);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL.SHOW_MESSAGES,
				jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					JSONArray jsonArray = response.getJSONArray("array");
					messages = JsonToBean.getMessages(jsonArray.toString());
					messageNum = messages.size();
					if(messageNum > 0){
						system_message_num.setVisibility(View.VISIBLE);
						system_message_num.setText(""+messageNum);
					}else{
						system_message_num.setVisibility(View.GONE);
						system_message_num.setText("");
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

}
