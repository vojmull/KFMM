package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListView_messages extends ArrayAdapter<String> {

    private List<Integer> imagesId;
    private List<String> names;
    private List<String> surnames;
    private List<String> messages;
    private List<String> times;
    private Activity context;

    public CustomListView_messages(@NonNull Context context, List<String> surnames, List<String> names, List<Integer> imagesId, List<String> messages, List<String> times){
        super(context,R.layout.messages_listview_detail,names);

        this.context = (Activity) context;
        this.names = names;
        this.surnames=surnames;
        this.imagesId=imagesId;
        this.messages=messages;
        this.times=times;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder=null;
        if(r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.messages_listview_detail,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)r.getTag();
        }
        viewHolder.ivw.setImageResource(imagesId.get(position));
        viewHolder.tvnames1.setText(names.get(position));
        viewHolder.tvsurnames2.setText(surnames.get(position));
        viewHolder.tvmessages.setText(messages.get(position));
        viewHolder.tvtimes.setText(times.get(position));


        return r;
    }
    class ViewHolder{
        TextView tvnames1;
        TextView tvsurnames2;
        ImageView ivw;
        TextView tvmessages;
        TextView tvtimes;

        ViewHolder(View v){
        tvnames1 = (TextView) v.findViewById(R.id.name_messages_textView);
        tvsurnames2 = (TextView)v.findViewById(R.id.surname_messages_textView);
        tvmessages=(TextView)v.findViewById(R.id.message_messages_textView);
        tvtimes=(TextView)v.findViewById(R.id.time_messages_textView);
        ivw = (ImageView)v.findViewById(R.id.img_messages_imageView);
        }
    }
}
