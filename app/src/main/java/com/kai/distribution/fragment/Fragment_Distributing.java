package com.kai.distribution.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.kai.distribution.R;
import com.kai.distribution.adapter.Distributing_listview_adapter;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.entity.Distributed;
import com.kai.distribution.entity.Distributing;
import com.kai.distribution.utils.JsonToBean;
import com.kai.distribution.utils.RsSharedUtil;
import com.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private Handler handler = new Handler();
    private int pageIndex = 1;

    private List<Distributing> newDatas;
    private JSONArray unparsedNewDatas;
    private List<Distributing> distributingList = new ArrayList<>();


    private static final String TAG = "Fragment_Distributing";
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
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.distributing_list_view);

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
        listview_adapter = new Distributing_listview_adapter(getActivity(),R.layout.takeoutfodd_content,distributingList);
        show_takeoutfood.setAdapter(listview_adapter);

        int item_amount=show_takeoutfood.getCount();
        show_count.setText("配送中"+Integer.toString(item_amount)+")");


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            String result = data.getExtras().getString("result");
            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
        }
    }


    private void initData(){
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("debug","postDelayed");
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 1);

        ptrClassicFrameLayout.setLoadMoreEnable(true);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //添加刷新事件
                        Log.e("debug","setPtrHandler");
                        pageIndex = 1;
                        getDistributedListByHTTP();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 1000);
            }
        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //添加加载事件
                        Log.e("debug","setOnLoadMoreListener");
                        pageIndex++;
                        getDistributedListByHTTP();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }


    /*
    TODO 显示待送餐订单列表接口
url：http:// milk345.imwork.net:13607/Canteen/worker/showOrders
方法：POST
入参：
 {
"workerId":配送员Id（int）,
"code":无时间戳的令牌（string）,
"buildingId":建筑Id（int）(查询全部时为0)
}
Content-Type: application/json
描述：
状态200返回值
返回类型为JSONArray，对于每个JSONObject，都有以下属性：
{
" groupId":箱子Id(int)
" sendMoney":配送费(long)
" getAccount":取餐号(string)
"statu":订单状态(int)
" canteen":餐厅名(string)
" area":校区代号(int)
" goods":餐品和对应的数量(JSONArray)
" account":订单号(string)
" workerName":配送员名字(string)
" workerPhone":配送员手机号(string)
" payMoney":实付款(long)
" stuName":学生名字(string)
" phoneNumber":学生手机号(string)
" time":下单时间毫秒数(long)
" address":配送地址(string)
" pay":支付方式代号(int) （校园卡0，微信2，支付宝1）
" sendTimeBegin":送达开始时间毫秒数(long)
" sendTimeEnd ":送达结束时间毫秒数(long)
" realTime":实际送达时间毫秒数(long)
" couLimitMoney":优惠券使用限制金额(long)(单位:分)(无优惠券时返回0)
" couDecreaseMoney":优惠券减少金额(long)(单位:分) (无优惠券时返回0)
" actLimitMoney":优惠活动限制金额(long)(单位:分) (无优惠活动时返回0)
" actDecreaseMoney":优惠活动减少金额(long)(单位:分) (无优惠活动时返回0)
" orderId":订单Id(int)
" goodNumber":订单Id(int)
"buildingId":建筑Id（int）
"buildingId":建筑名字（string）
}
{
goods:
" goodName":餐品名字（string）
" number":每个餐品的数量(int)
" goodId":餐品Id(int)
}
失败时，返回类型为JSONArray，对于第一个JSONObject，可能有以下属性：
{
"result":"noorder"（无订单）
"result":"no such a worker"（失败，该配送员不存在）
"result":" offline"（配送员不在线）
"result":" wrongcode"（失败，令牌错误）
"result":" longTimeOffLine"（失败，配送员掉线了）
}
     */

    private void getDistributedListByHTTP(){

        JSONObject jsonObject = new JSONObject();
        String url = Constants.URL.DISTRIBUTING_URL;

//		Student student = getStudent();
        try {

            jsonObject.put("code", RsSharedUtil.getString(getActivity(),"code"));
            jsonObject.put("workerId", RsSharedUtil.getInt(getActivity(),Constants.KEY.WORK_ID));
            jsonObject.put("buildingId", buildingId);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("Fragment_Distributing", response.toString());
                            unparsedNewDatas = response;
                            manageData();//处理数据
                            Log.i("onResponse", "success");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            String result = response.toString();

                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fragment_Distributing","error:"+error.toString());
            }
        });

        MyApplication.getRequestQueue().add(jsonArrayRequest);
    }

    private void manageData(){
        if (newDatas != null){
            newDatas.clear();
        }else {
            newDatas = new ArrayList<>();
        }

        if (unparsedNewDatas == null || unparsedNewDatas.length()==0){
            Toast.makeText(getActivity(),"没有代送餐记录",Toast.LENGTH_SHORT).show();
            return;
        } else {

					/*TODO: 16/7/18
					  解析成Bean
					  */


            Log.e("Fragment_Distributing","JsonToBean");

            newDatas = JsonToBean.getDistributings(unparsedNewDatas.toString());

            Log.e("Fragment_Distributing","buildingName : ");
            unparsedNewDatas = null;


            distributingList.clear();
            distributingList.addAll(newDatas);
            listview_adapter.notifyDataSetChanged();

        }

    }




//    @Override
//    public void onStart() {
//        super.onStart();
//        if (listview_adapter != null) {
//            listview_adapter.notifyDataSetChanged();
//        }
//    }


    @Override
    public void onDestroy() {
        Log.e(TAG,TAG + " : onDestroy()");
        super.onDestroy();
    }
}
