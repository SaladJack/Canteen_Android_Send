package com.kai.distribution.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kai.distribution.R;
import com.kai.distribution.entity.Distributed;
import com.kai.distribution.utils.TimeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by tusm on 16/7/18.
 */
public class Distributed_listview_adapter extends ArrayAdapter<Distributed> {
    private int resourceId;
    private Context context;

    public Distributed_listview_adapter(Context context, int resourceId, List<Distributed> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Distributed distributed = getItem(position);

        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.distributed_person_name = (TextView) view.findViewById(R.id.distributed_person_name);
            viewHolder.distributed_which_area = (TextView) view.findViewById(R.id.distributed_which_area);
            viewHolder.distributed_which_time = (TextView) view.findViewById(R.id.distributed_which_time);
            viewHolder.distributed_good_number = (TextView)view.findViewById(R.id.goodNumber);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.distributed_person_name.setText(distributed.getStuName());
        viewHolder.distributed_which_area.setText(distributed.getAddress());
        viewHolder.distributed_good_number.setText("[" + distributed.getGoodNumber() + "份]");
        viewHolder.distributed_which_time.setText(String.valueOf(TimeUtils.MillisToString(distributed.getRealTime())));
        if (distributed.getFlag() == 1){
            viewHolder.distributed_which_time.setTextColor(context.getResources().getColor(R.color.red));
        }



        return view;
    }



    static class ViewHolder {
        public TextView distributed_person_name;
        public TextView distributed_which_area;
        public TextView distributed_which_time;
        public TextView distributed_good_number;
    }


}

