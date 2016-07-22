package com.kai.distribution.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.kai.distribution.R;
import com.kai.distribution.activity.HomeActivity;
import com.kai.distribution.adapter.Distributing_listview_adapter;
import com.kai.distribution.app.Constants;
import com.kai.distribution.entity.MessageEvent;
import com.kai.distribution.utils.DistrbutingUtils;
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
    private final String[] spinner_text = { "北一", "北二", "北三", "北四", "北五", "北六",
            "北七", "北八", "北九", "北十", "北十一", "北十二", "北十三", "北十四", "北十五", "北十六",
            "北十七", "北十八", "北十九", "北二十" };

    //TODO 下面这两个参数有映射关系
    private String current_area;
    private int buildingId = 0;

    private ImageButton scan;




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

        spinner_content = new ArrayList<String>();
        for (int i = 0; i < spinner_text.length; i++) {
            spinner_content.add(spinner_text[i]);
        }
        spinner_adapter = new ArrayAdapter(getActivity(), R.layout.show_distributed_spinner_text,R.id.spinner_tv,spinner_content);
        spinner_adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(spinner_adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                current_area = (String) spinner.getSelectedItem();
                listview_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        show_takeoutfood = (ListView) view.findViewById(R.id.show_takeoutfood);
        listview_adapter = new Distributing_listview_adapter(getActivity(),R.layout.takeoutfodd_content, Constants.GLOBAL.distributingList);
        show_takeoutfood.setAdapter(listview_adapter);

        int item_amount=show_takeoutfood.getCount();
        show_count.setText("配送中"+"("+item_amount+")");


        scan.setOnClickListener(this);



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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK){
//            String result = data.getExtras().getString("result");
//            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
//        }
//    }


    private void initData() {
        listview_adapter.notifyDataSetChanged();
    }

    //监听区域变化并获得
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        Log.e(TAG,"onMessageEvent()");
        area.setText(""+messageEvent.getIntMsg());
    }



    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
