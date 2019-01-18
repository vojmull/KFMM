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
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomListView_manageFriends2 extends BaseAdapter {
    private Integer[] imagesId;

    private List<String> names;
    private List<String> surnames;
    private List<Integer> ids;
    private Activity context;

    LayoutInflater mInflater;

    public CustomListView_manageFriends2(@NonNull Context context, List<String> surnames,List<String> names,Integer[] imagesId,List<Integer> ids) {

        this.context = (Activity) context;
        this.names = names;
        this.surnames=surnames;
        this.imagesId=imagesId;
        this.ids=ids;
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
        badd.setBackgroundColor(Integer.parseInt(context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("newColor","None")));
        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendGetAddFriend spaf = new SendGetAddFriend();
                try {
                    String response = spaf.execute(String.valueOf(ids.get(position)),
                            context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("AppUrl","None")
                            ,context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("Token","None")
                            ,context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("UserId","None"))
                            .get();
                    response = response.substring(1,response.length()-2);
                    if(response.equals("OK")){
                        Toast.makeText(context, "a friend request has been sent", Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }
}
