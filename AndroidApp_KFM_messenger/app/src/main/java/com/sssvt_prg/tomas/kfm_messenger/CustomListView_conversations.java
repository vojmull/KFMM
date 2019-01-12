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
    private List<String> authors;
    private Activity context;

    LayoutInflater mInflater;

    public CustomListView_conversations(@NonNull Context context, List<String> conversations, List<String> delievered, List<String> seen, List<String> times, List<String> authors) {
        //super(context,R.layout.conversation_listview_detail);

        this.context = (Activity) context;
        this.conversations = conversations;
        this.seen = seen;
        this.delievered = delievered;
        this.times = times;
        this.authors=authors;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }

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

        if(authors.get(position).equals(LoginActivity.UserID)) {
            v.setBackgroundColor(LoginActivity.newColor);

        }

        TextView conversationTV = (TextView) v.findViewById(R.id.message_detail_textView);
        TextView timeTV = (TextView) v.findViewById(R.id.time_detail_textView);

        String conv = conversations.get(position);
        String tim = times.get(position);


        conversationTV.setText(conv);
        timeTV.setText(tim);

        return v;
    }
}
