package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListView_manageFriends2 extends BaseAdapter {
    private Integer[] imagesId;
    private List<String> names;
    private List<String> surnames;
    //private String [] names;
    //private String []surnames;
    private Activity context;

    LayoutInflater mInflater;

    public CustomListView_manageFriends2(@NonNull Context context, List<String> surnames,List<String> names,Integer[] imagesId) {
        //super(context,R.layout.conversation_listview_detail);

        this.context = (Activity) context;
        this.names = names;
        this.surnames=surnames;
        this.imagesId=imagesId;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);



    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.managefriends_listview_details,null);




        TextView tvw1 = (TextView) v.findViewById(R.id.name_manageFriends_textView);
        TextView tvw2 = (TextView)v.findViewById(R.id.surname_manageFriends_textView);
        ImageView ivw = (ImageView)v.findViewById(R.id.img_manageFriends_imageView);
        Button badd = (Button)v.findViewById(R.id.add_manageFriends_listview_button);


        //ivw.setImageResource(imagesId[position]);
        ivw.setImageResource(R.drawable.icons8_person_48);
        tvw1.setText(names.get(position));
        tvw2.setText(surnames.get(position));
        //viewHolder.tvw1.setText(names[position]);
        //viewHolder.tvw2.setText(surnames[position]);
        badd.setBackgroundColor(LoginActivity.newColor);
        // tady dat onClickListener na otevreni zpravy

        return v;
    }
}
