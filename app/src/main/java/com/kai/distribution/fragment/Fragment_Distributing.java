package com.kai.distribution.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kai.distribution.R;
import com.kai.distribution.adapter.Distributing_listview_adapter;
import com.kai.distribution.app.Constants;
import com.kai.distribution.entity.Distributing;
import com.kai.distribution.utils.QRUtils;
import com.kai.distribution.utils.TimeUtils;
import com.orhanobut.logger.Logger;
import com.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Distributing extends Fragment implements View.OnClickListener
{
    private View view;
    private TextView area;
    private TextView service_time;
    private TextView show_count;
    private Spinner spinner;
    private ListView show_takeoutfood;
    private List<String> spinner_content;
    private ArrayAdapter<String> spinner_adapter;
    private Distributing_listview_adapter listview_adapter;


    private List<Distributing> distributingList;


    //TODO 下面这两个参数有映射关系
    private String current_area = "全部";
    private int buildingId = 0;
    private TextView sendArea;
    private ImageButton scan;
    private ImageButton createQR;




    private static final String TAG = "Fragment_Distributing";

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_disttributing, container, false);
            initView();
            initData();

        return view;
    }



    private void initView() {
        area = (TextView) view.findViewById(R.id.area);
        service_time = (TextView) view.findViewById(R.id.service_time);
        show_count=(TextView) view.findViewById(R.id.show_count);
        spinner = (Spinner) view.findViewById(R.id.show_listview);
        scan = (ImageButton)view.findViewById(R.id.scan);
        sendArea = (TextView)view.findViewById(R.id.sendArea);
        createQR = (ImageButton)view.findViewById(R.id.createQR);

        createQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.qr_layout);
                ImageView qrImageView = (ImageView) window.findViewById(R.id.qrImage);
                final Button qrBack = (Button)window.findViewById(R.id.qr_back);
                Log.e(TAG,""+qrImageView.getWidth());
                QRUtils.createQRImage(qrImageView,"comfirm");//这里存储二维码信息
                qrBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


            }
        });


        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                current_area = (String) spinner.getSelectedItem();
                selectLocation(current_area);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });
        distributingList = new ArrayList<>();
        show_takeoutfood = (ListView) view.findViewById(R.id.show_takeoutfood);
        listview_adapter = new Distributing_listview_adapter(getActivity(),R.layout.takeoutfodd_content, distributingList);
        show_takeoutfood.setAdapter(listview_adapter);

        spinner_content = new ArrayList<String>();
        spinner_content.add("全部");



        spinner_adapter = new ArrayAdapter(getActivity(), R.layout.show_distributing_spinner_text, R.id.spinner_tv, spinner_content);
        spinner_adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(spinner_adapter);

        ViewTreeObserver vto = spinner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                spinner.setDropDownVerticalOffset(spinner.getHeight());
                ViewTreeObserver obs = spinner.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }
        });



        scan.setOnClickListener(this);



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Log.e(TAG,"DISPATCH");
                String res = data.getExtras().getString("result");
                if (TextUtils.isEmpty(res))
                    return;
                if (res.equals("success")) {
                    Log.e(TAG, "success");
                    Constants.GLOBAL.HAVE_SCANNED = true;
                    Log.e("Click","click : "+Constants.GLOBAL.HAVE_SCANNED );
                    Message msg = Message.obtain();
                    msg.what = Constants.CODE.SCAN;
                    Log.e(TAG,"toAfterScanning");
                    EventBus.getDefault().post(msg);
                } else {
                    Toast.makeText(getContext(), "二维码数据错误", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.scan:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }




    private void initData() {
        listview_adapter.notifyDataSetChanged();
    }

    //监听区域、时间变化并更改UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent( List<Distributing> distributings) {
        //设置区域
        sendArea.setText("" + distributings.get(0).getSendArea()+"区");
        //设置时间段
        service_time.setText(TimeUtils.MillisToString(distributings.get(0).getSendTimeBegin())
                + "~" + TimeUtils.MillisToString(distributings.get(0).getSendTimeEnd()));

        show_count.setText("配送中"+"("+distributings.size()+")");

        //设置配送地区spinner
        synchronized (this) {
            if (distributingList != null) {
                distributingList.clear();
            }

            if (spinner_content != null) {
                spinner_content.clear();
                spinner_content.add("全部");
            }
            int size = distributings.size();
            for (int i = 0; i < size; ++i){
                if (!spinner_content.contains(distributings.get(i).getBuildingName()))
                    spinner_content.add(distributings.get(i).getBuildingName());
            }


            if (spinner_adapter == null) {

            }else{
                spinner_adapter.notifyDataSetChanged();
            }
        }

        selectLocation(current_area);



    }




    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (spinner_content != null) {
            spinner_content.clear();
            spinner_content = null;
        }

        if (spinner_adapter != null){
            spinner_adapter.clear();
            spinner_adapter = null;
        }
        super.onDestroy();
    }


    private void selectLocation(String location){
        distributingList.clear();
        int size = Constants.GLOBAL.distributingList.size();
        if (location.equals("全部")){
            distributingList.addAll(Constants.GLOBAL.distributingList);
        }else {
            String str;
            for (int i = 0; i < size; ++i) {
                str = Constants.GLOBAL.distributingList.get(i).getBuildingName();
                if (str.equals(location)) {
                    distributingList.add(Constants.GLOBAL.distributingList.get(i));
                }
            }
        }

        Logger.e("size: "+distributingList.size());

        listview_adapter.notifyDataSetChanged();
    }

}
