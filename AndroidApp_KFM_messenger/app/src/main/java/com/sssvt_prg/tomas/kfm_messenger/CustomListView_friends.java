package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListView_friends extends ArrayAdapter<String> {

    private Integer[] imagesId;
    //private List<String> names;
    //private List<String> surnames;
    private String [] names;
    private String []surnames;
    private Activity context;
    public CustomListView_friends(@NonNull Context context, String[] surnames,String[] names,Integer[] imagesId){
        super(context,R.layout.friends_litview_detail,names);

        this.context = (Activity) context;
        this.names = names;
        this.surnames=surnames;
        this.imagesId=imagesId;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder=null;
        if(r==null)
        {
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.friends_litview_detail,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)r.getTag();
        }
        viewHolder.ivw.setImageResource(imagesId[position]);
        //viewHolder.tvw1.setText(names.get(position));
        //viewHolder.tvw2.setText(surnames.get(position));
        viewHolder.tvw1.setText(names[position]);
        viewHolder.tvw2.setText(surnames[position]);
        viewHolder.bchat.setBackgroundColor(LoginActivity.newColor);


        return r;
    }
    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        Button bchat;

        ViewHolder(View v){
        tvw1 = (TextView) v.findViewById(R.id.name_friends_textView);
        tvw2 = (TextView)v.findViewById(R.id.surname_friends_textView);
        ivw = (ImageView)v.findViewById(R.id.img_friends_imageView);
        bchat = (Button)v.findViewById(R.id.chat_friends_listview_button);
        }
    }
}
