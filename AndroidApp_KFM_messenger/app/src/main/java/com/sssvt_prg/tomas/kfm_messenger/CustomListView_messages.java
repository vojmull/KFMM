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

public class CustomListView_messages extends ArrayAdapter<String> {

    private Integer[] imagesId;
    private String[] names;
    private String[] surnames;
    private String[] messages;
    private String[] times;
    private Activity context;
    public CustomListView_messages(@NonNull Context context, String[] surnames, String[] names, Integer[] imagesId,String[] messages,String[] times){
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
        viewHolder.ivw.setImageResource(imagesId[position]);
        viewHolder.tvnames1.setText(names[position]);
        viewHolder.tvsurnames2.setText(surnames[position]);
        viewHolder.tvmessages.setText(messages[position]);
        viewHolder.tvtimes.setText(times[position]);


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
