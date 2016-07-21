package com.kai.distribution.adapter;

import android.app.AlertDialog;
import android.content.Context;
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

import com.kai.distribution.R;
import com.kai.distribution.entity.Distributed;
import com.kai.distribution.entity.Distributing;

import java.util.List;

/**
 * Created by tusm on 16/7/19.
 */
public class Distributing_listview_adapter extends ArrayAdapter<Distributing> {
    private Context context;
    private String current_area;
    private int resourceId;

    public Distributing_listview_adapter(Context context, int resourceId, List<Distributing> objects) {
        super(context, resourceId, objects);
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        final Distributing distributing = getItem(position);
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.takeoutfodd_content, viewgroup, false);
            holder = new ViewHolder();
            holder.which_area = (TextView) view.findViewById(R.id.which_area);
            holder.which_people = (TextView) view.findViewById(R.id.which_people);
            holder.is_out = (Button) view.findViewById(R.id.is_out);
            holder.is_confirm = (Button) view.findViewById(R.id.is_confirm);
            holder.delivered=(ImageButton) view.findViewById(R.id.delivered);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }



        holder.which_area.setText(distributing.getBuildingName());
        holder.which_people.setText(distributing.getStuName());


        holder.is_out.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.confirm_out_dialog);
                Button yes = (Button) window.findViewById(R.id.yes);
                Button no = (Button) window.findViewById(R.id.no);
                yes.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        holder.is_out.setVisibility(View.GONE);
                        alertDialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        alertDialog.dismiss();
                    }
                });
            }
        });

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
                        holder.is_confirm.setVisibility(View.GONE);
                        holder.delivered.setVisibility(View.VISIBLE);
                        holder.delivered.setEnabled(false);
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
    }

}


