package com.kai.distribution.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

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
import com.orhanobut.logger.Logger;

import org.apache.http.protocol.RequestUserAgent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private Drawable drawable;
    private List<ViewHolder> viewHolders = new ArrayList<>();

    public Distributing_listview_adapter(Context context, int resourceId, List<Distributing> objects) {
        super(context, resourceId, objects);
        this.context = context;
        this.resourceId = resourceId;


        drawable= context.getResources().getDrawable(R.mipmap.ic_phone_gray);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
    }


    private final int STATU_DELIVERED = 5;
    private final int STATU_ISOUT = 10;
    private final int FLAG_NOYELLOW = 0;
    private final int FLAG_YELLO = 1;

    private final int NOYELLO_DELIVERED = 1;
    private final int NOYELLO_ISOUT = 2;
    private final int YELLO_DELIVERED = 3;
    private final int YELLO_ISOUT = 4;




    @Override
    public View getView(final int position, View view, ViewGroup viewgroup) {
        final Distributing distributing = getItem(position);
        Logger.e("getView position:  " + position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.takeoutfodd_content, viewgroup, false);
            holder = new ViewHolder();
            viewHolders.add(holder);
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

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
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
                                     STATU_ISOUT,viewHolders.get(position));
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        alertDialog.dismiss();

                        confirmOrder(RsSharedUtil.getString(context,Constants.KEY.USER_CODE),
                                RsSharedUtil.getInt(context,Constants.KEY.WORK_ID),
                                distributing.getOrderId(),
                                STATU_ISOUT,viewHolders.get(position));
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

                Logger.e(viewHolders.get(position).toString());

                confirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub

                        Logger.e("确认按钮被点击");
                        alertDialog.dismiss();



                        confirmOrder(RsSharedUtil.getString(context,Constants.KEY.USER_CODE),
                                RsSharedUtil.getInt(context,Constants.KEY.WORK_ID),
                                distributing.getOrderId(),
                                STATU_DELIVERED,viewHolders.get(position));
                    }
                });
            }
        });
        return view;
    }



    public  static class ViewHolder {
        public TextView which_area, which_people;
        public Button is_out, is_confirm;
        public ImageButton delivered;
        public Button deliverToBuilding;
    }


    private void confirmOrder(String code, int workerId, int orderId, final int statu,final ViewHolder viewHolder){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code",code);
            jsonObject.put("workerId",workerId);
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
                            Logger.json(response.toString());
                            String res = response.getString("result");
                            if (res.equals("confirmsuccess")){
                                int flag = response.getInt("flag");
                                /*
                                " statu":订单状态（int）（5：确认送达，10：学生外出）
                                " flag":添加黄牌的标记（int）(0:不添加黄牌;1:添加黄牌)
                                */
                                Logger.e("statu:   "+ statu);
                               if(flag == 0 && statu == 5){
//                                   Message msg = Message.obtain();
//                                   msg.what = NOYELLO_DELIVERED;
//                                   mHandler.sendMessage(msg);

                                   viewHolder.is_confirm.setVisibility(View.GONE);
                                   viewHolder.is_out.setVisibility(View.GONE);
                                   viewHolder.delivered.setVisibility(View.VISIBLE);
                                   viewHolder.delivered.setEnabled(false);


                                   viewHolder.which_people.setEnabled(false);
                                   viewHolder.which_people.setTextColor(0xFFC7C6C6);
                                   viewHolder.which_people.setCompoundDrawables(drawable,null,null,null);
                               }else if (flag == 0 && statu == 10){
//                                   Message msg = Message.obtain();
//                                   msg.what = NOYELLO_ISOUT;
//                                   mHandler.sendMessage(msg);

                                   viewHolder.is_out.setVisibility(View.GONE);
                                   viewHolder.is_confirm.setVisibility(View.GONE);
                                   viewHolder.deliverToBuilding.setVisibility(View.VISIBLE);
                                   viewHolder.deliverToBuilding.setEnabled(false);


                                   viewHolder.which_people.setEnabled(false);
                                   viewHolder.which_people.setTextColor(0xFFC7C6C6);
                                   viewHolder.which_people.setCompoundDrawables(drawable,null,null,null);
                               }else if (flag == 1 && statu == 5){
//                                   Message msg = Message.obtain();
//                                   msg.what = YELLO_DELIVERED;
//                                   mHandler.sendMessage(msg);

                                   viewHolder.is_confirm.setVisibility(View.GONE);
                                   Logger.e(viewHolder.toString());
                                   Logger.e("是否可见"+(viewHolder.is_confirm.getVisibility()==View.VISIBLE));
                                   viewHolder.is_out.setVisibility(View.GONE);
                                   viewHolder.delivered.setVisibility(View.VISIBLE);
                                   viewHolder.delivered.setEnabled(false);

                                   viewHolder.which_people.setEnabled(false);
                                   viewHolder.which_people.setTextColor(0xFFC7C6C6);

                                   viewHolder.which_people.setCompoundDrawables(drawable,null,null,null);
                                   showCardDialog();

                               }else if (flag == 1 && statu == 10){
//                                   Message msg = Message.obtain();
//                                   msg.what = YELLO_ISOUT;
//                                   mHandler.sendMessage(msg);

                                   viewHolder.is_out.setVisibility(View.GONE);
                                   viewHolder.is_confirm.setVisibility(View.GONE);
                                   viewHolder.deliverToBuilding.setVisibility(View.VISIBLE);

                                   viewHolder.which_people.setEnabled(false);
                                   viewHolder.which_people.setTextColor(0xFFC7C6C6);
                                   viewHolder.which_people.setCompoundDrawables(drawable,null,null,null);
                                   showCardDialog();
                               }
                            }
                            else if (res.equals("confirfail")){

                                Toast.makeText(context, "确认失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Logger.e(error.toString());
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


