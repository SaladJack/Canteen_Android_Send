package com.kai.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kai.distribution.R;
import com.kai.distribution.entity.Message;
import com.kai.distribution.utils.TimeUtils;

import java.util.List;

/**
 * Created by tusm on 16/7/25.
 */
public class Message_listview_adapter extends ArrayAdapter<Message> {

    private List<Message> messages;
    private int resourceId;
    private Context context;
    private ViewHolder viewholder;

    public Message_listview_adapter(Context context, int resourceId, List<Message> messages) {
        super(context, resourceId, messages);
        this.context = context;
        this.messages = messages;
        this.resourceId = resourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Message message  = getItem(position);
        if (convertView == null){
            view  = LayoutInflater.from(context).inflate(resourceId,parent,false);
            viewholder = new ViewHolder();
            viewholder.messageContent = (TextView) view.findViewById(R.id.message_content);
            viewholder.messageDate = (TextView)view.findViewById(R.id.message_date);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }

        viewholder.messageContent.setText(message.getContent());
        viewholder.messageDate.setText(TimeUtils.MillisToString_Point(message.getTime()));

        return view;
    }

    static class ViewHolder{
        TextView messageContent;
        TextView messageDate;
    }
}
