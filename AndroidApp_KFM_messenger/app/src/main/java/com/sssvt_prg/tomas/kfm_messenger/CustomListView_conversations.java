package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListView_conversations extends BaseAdapter {
    private List<String> conversations;
    private List<Boolean> seen;
    private List<Boolean> delievered;
    private List<String> times;
    private List<String> authors;
    private Activity context;

    LayoutInflater mInflater;

    public CustomListView_conversations(@NonNull Context context, List<String> conversations, List<Boolean> delievered, List<Boolean> seen, List<String> times, List<String> authors) {
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

        /*if(authors.get(position).equals(LoginActivity.UserID)) {
            v.setBackgroundColor(LoginActivity.newColor);

        }*/

        TextView conversationTV = (TextView) v.findViewById(R.id.message_detail_textView);
        TextView timeTV = (TextView) v.findViewById(R.id.time_detail_textView);
        ImageView imageIV = (ImageView) v.findViewById(R.id.seen_detail_imageView);

        /*if(authors.get(position).equals(LoginActivity.UserID)) {
            v.setBackgroundColor(LoginActivity.newColor);

        }*/
        if(authors.get(position).equals(LoginActivity.UserID)) {
            //conversationTV.setBackgroundColor(LoginActivity.newColor);

            conversationTV.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_darker));

            //conversationTV.setGravity(Gravity.END);
            conversationTV.setGravity(Gravity.END);
        }
        else{

        }

        String conv = conversations.get(position);
        String tim = times.get(position);


        conversationTV.setText(conv);
        timeTV.setText(tim);


        if(seen.get(position)==true)
        {
            try{
                if(seen.get(position+1).equals(false)){
                    imageIV.setImageResource(R.drawable.seen);
                }
                else{
                    imageIV.setVisibility(View.INVISIBLE);
                }
            }
            catch(Exception ex){
                imageIV.setImageResource(R.drawable.seen);
            }

        }
        else if(delievered.get(position)==true ||delievered.get(position)==false){
            try{
                if(delievered.get(position+1).equals(false)){
                    imageIV.setImageResource(R.drawable.delievered);
                }
                else{
                    imageIV.setVisibility(View.INVISIBLE);
                }
            }
            catch(Exception ex){
                imageIV.setImageResource(R.drawable.delievered);
            }

        }


        return v;
    }
}
