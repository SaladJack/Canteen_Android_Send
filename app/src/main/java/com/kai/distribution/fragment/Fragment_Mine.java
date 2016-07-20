package com.kai.distribution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kai.distribution.R;
import com.kai.distribution.activity.Bind_Phone_Activity;
import com.kai.distribution.activity.Binded_Phone_Activity;
import com.kai.distribution.activity.Changed_Password_Activity;
import com.kai.distribution.activity.LoginActivity;
import com.kai.distribution.activity.ScanActivity;
import com.kai.distribution.activity.Setup_Activity;
import com.kai.distribution.activity.SysMessage_Activity;
import com.kai.distribution.activity.Work_Clipboard_Activity;
import com.kai.distribution.app.Constants;
import com.kai.distribution.utils.RsSharedUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;


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
	@ViewInject(R.id.ll_login)
	private LinearLayout ll_login;
	@ViewInject(R.id.phone_number)
	private TextView phone_number;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_mine, container,false);
		ViewUtils.inject(this, view);
		initView();
		return view;
	}

	//返回该Fragment需要刷新界面 public不能更改
	public void initView() {
		if (!RsSharedUtil.getString(getActivity(), Constants.KEY.USER_CODE).equals("") ||
				!RsSharedUtil.getString(getActivity(), Constants.KEY.USER_CODE).equalsIgnoreCase("null")) {
			ll_user.setVisibility(View.VISIBLE);
			ll_login.setVisibility(View.GONE);
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
			ll_login.setVisibility(View.VISIBLE);
			ll_user.setVisibility(View.GONE);
		}

	}


	@OnClick({R.id.ll_work,R.id.ll_password,R.id.ll_bind,R.id.ll_set,R.id.ll_system,R.id.ll_login})
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
					startActivity(intent);
					break;
				case R.id.ll_login:
					intent=new Intent(getActivity(),LoginActivity.class);
					startActivity(intent);
					break;

			}
		}
		else {
			intent=new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
		}
	}

}
