package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListView_conversations extends BaseAdapter {
    private List<String> conversations;
    private List<String> seen;
    private List<String> delievered;
    private List<String> times;
    private Activity context;

    LayoutInflater mInflater;

    public CustomListView_conversations(@NonNull Context context, List<String> conversations, List<String> delievered, List<String> seen, List<String> times) {
        //super(context,R.layout.conversation_listview_detail);

        this.context = (Activity) context;
        this.conversations = conversations;
        this.seen = seen;
        this.delievered = delievered;
        this.times = times;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }
/*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder=null;
        if(r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.conversation_listview_detail,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)r.getTag();
        }

        viewHolder.tvconversations.setText(conversations.get(position));
        viewHolder.tvseen.setText(seen.get(position));
        viewHolder.tvdelievered.setText(delievered.get(position));
        viewHolder.tvtimes.setText(times.get(position));

        return r;
    }
    class ViewHolder{
        TextView tvconversations;
        TextView tvseen;
        TextView tvdelievered;
        TextView tvtimes;

        ViewHolder(View v){
            tvconversations = (TextView) v.findViewById(R.id.message_detail_textView);
            //tvseen = (TextView)v.findViewById(R.id.);
            //tvdelievered=(TextView)v.findViewById(R.id.message_messages_textView);
            tvtimes=(TextView)v.findViewById(R.id.time_detail_textView);
        }
    }*/

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Object getItem(int position) {
        return conversations.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.conversation_listview_detail,null);

        TextView conversationTV = (TextView) v.findViewById(R.id.message_detail_textView);
        TextView timeTV = (TextView) v.findViewById(R.id.time_detail_textView);

        String conv = conversations.get(position);
        String tim = times.get(position);


        conversationTV.setText(conv);
        timeTV.setText(tim);

        return v;
    }
}
