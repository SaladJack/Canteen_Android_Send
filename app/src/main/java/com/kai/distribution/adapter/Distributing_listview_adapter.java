package com.kai.distribution.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.entity.Distributed;
import com.kai.distribution.entity.Distributing;
import com.kai.distribution.utils.RsSharedUtil;

import org.apache.http.protocol.RequestUserAgent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by tusm on 16/7/19.
 */
public class Distributing_listview_adapter extends ArrayAdapter<Distributing> {
    private Context context;
    private String current_area;
    private int resourceId;
    private ViewHolder holder;

    public Distributing_listview_adapter(Context context, int resourceId, List<Distributing> objects) {
        super(context, resourceId, objects);
        this.context = context;
        this.resourceId = resourceId;
    }


    private final int STATU_DELIVERED = 5;
    private final int STATU_ISOUT = 10;
    private final int FLAG_NOYELLOW = 0;
    private final int FLAG_YELLO = 1;

    private  final int NOYELLO_DELIVERED = 1;
    private  final int NOYELLO_ISOUT = 2;
    private  final int YELLO_DELIVERED = 3;
    private  final int YELLO_ISOUT = 4;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case NOYELLO_DELIVERED:
                    holder.is_confirm.setVisibility(View.GONE);
                    holder.delivered.setVisibility(View.VISIBLE);
                    holder.delivered.setEnabled(false);


                    holder.which_people.setEnabled(false);
                    holder.which_people.setTextColor(0xFFC7C6C6);
                    holder.which_area.setBackground(context.getResources().getDrawable(R.mipmap.ic_phone_gray));
                    break;
                case NOYELLO_ISOUT:
                    holder.is_out.setVisibility(View.GONE);
                    holder.is_confirm.setVisibility(View.GONE);
                    holder.deliverToBuilding.setVisibility(View.VISIBLE);
                    holder.deliverToBuilding.setEnabled(false);


                    holder.which_people.setEnabled(false);
                    holder.which_people.setTextColor(0xFFC7C6C6);
                    holder.which_area.setBackground(context.getResources().getDrawable(R.mipmap.ic_phone_gray));

                    break;
                case YELLO_DELIVERED:
                    holder.is_confirm.setVisibility(View.GONE);
                    holder.delivered.setVisibility(View.VISIBLE);
                    holder.delivered.setEnabled(false);

                    holder.which_people.setEnabled(false);
                    holder.which_people.setTextColor(0xFFC7C6C6);
                    holder.which_area.setBackground(context.getResources().getDrawable(R.mipmap.ic_phone_gray));
                    showCardDialog();

                    break;
                case YELLO_ISOUT:
                    holder.is_out.setVisibility(View.GONE);
                    holder.is_confirm.setVisibility(View.GONE);
                    holder.deliverToBuilding.setVisibility(View.VISIBLE);

                    holder.which_people.setEnabled(false);
                    holder.which_people.setTextColor(0xFFC7C6C6);
                    holder.which_area.setBackground(context.getResources().getDrawable(R.mipmap.ic_phone_gray));
                    showCardDialog();

                    break;

            }
        }
    };
    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        final Distributing distributing = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.takeoutfodd_content, viewgroup, false);
            holder = new ViewHolder();
            holder.which_area = (TextView) view.findViewById(R.id.which_area);
            holder.which_people = (TextView) view.findViewById(R.id.which_people);
            holder.is_out = (Button) view.findViewById(R.id.is_out);
            holder.is_confirm = (Button) view.findViewById(R.id.is_confirm);
            holder.delivered = (ImageButton) view.findViewById(R.id.delivered);
            holder.deliverToBuilding = (Button) view.findViewById(R.id.deliverToBuilding);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.which_area.setText(distributing.getBuildingName());
        holder.which_people.setText(distributing.getStuName());
        holder.which_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + distributing.getPhoneNumber()));

                context.startActivity(intent);
            }
        });

        //外出按钮
        holder.is_out.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.confirm_out_dialog);
                Button yes = (Button) window.findViewById(R.id.yes);
                Button no = (Button) window.findViewById(R.id.no);
                // TODO:外出确认 网络请求  statu = 10(学生外出)
                yes.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        alertDialog.dismiss();
                        // TODO: 16/7/22 判断是否超时

                        confirmOrder(RsSharedUtil.getString(context,Constants.KEY.USER_CODE),
                                     RsSharedUtil.getInt(context,Constants.KEY.WORK_ID),
                                     distributing.getOrderId(),
                                     STATU_ISOUT);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        //确认送达按钮
        holder.is_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.confirm_dialog);
                Button confirm=(Button) window.findViewById(R.id.confirm);

                confirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub
                        confirmOrder(RsSharedUtil.getString(context,Constants.KEY.USER_CODE),
                                RsSharedUtil.getInt(context,Constants.KEY.WORK_ID),
                                distributing.getOrderId(),
                                STATU_ISOUT);
                        alertDialog.dismiss();
                    }
                });
            }
        });
        return view;
    }



    static class ViewHolder {
        public TextView which_area, which_people;
        public Button is_out, is_confirm;
        public ImageButton delivered;
        public Button deliverToBuilding;
    }


    private void confirmOrder(String code, int workerId, int orderId, final int statu){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code",code);
            jsonObject.put("workedId",workerId);
            jsonObject.put("orderId",orderId);
            jsonObject.put("statu",statu);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        " orderId":订单Id（int）
        " statu":订单状态（int）（5：确认送达，10：学生外出）
        " flag":添加黄牌的标记（int）(0:不添加黄牌;1:添加黄牌)
        " day":当日凌晨时间毫秒数（long）
        */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL.CONFIRM_ORDER, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("result");
                            if (res.equals("confirmsuccess")){
                                int flag = 0;//获得黄牌

                               if(flag == 0 && statu == 5){
                                   Message msg = Message.obtain();
                                   msg.what = NOYELLO_DELIVERED;
                                   mHandler.sendMessage(msg);
                               }else if (flag == 0 && statu == 10){
                                   Message msg = Message.obtain();
                                   msg.what = NOYELLO_ISOUT;
                                   mHandler.sendMessage(msg);
                               }else if (flag == 1 && statu == 5){
                                   Message msg = Message.obtain();
                                   msg.what = YELLO_DELIVERED;
                                   mHandler.sendMessage(msg);
                               }else if (flag == 1 && statu == 10){
                                   Message msg = Message.obtain();
                                   msg.what = YELLO_ISOUT;
                                   mHandler.sendMessage(msg);
                               }
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

    private void showCardDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.yellowcard_dialog);
        Button confirm=(Button) window.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}


