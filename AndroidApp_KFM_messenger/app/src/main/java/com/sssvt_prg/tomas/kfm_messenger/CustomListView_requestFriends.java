package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class CustomListView_requestFriends extends BaseAdapter {
    private Integer[] imagesId;

    private List<String> names;
    private List<Integer> ids;
    private Activity context;

    LayoutInflater mInflater;

    public CustomListView_requestFriends(@NonNull Context context,List<String> names,Integer[] imagesId,List<Integer> ids) {

        this.context = (Activity) context;
        this.names = names;
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
        View v = mInflater.inflate(R.layout.request_friends_listview_detail,null);




        TextView tvw1 = (TextView) v.findViewById(R.id.name_requestFriends_textView);
        ImageView ivw = (ImageView)v.findViewById(R.id.img_friends_imageView);
        Button bconfirm = (Button)v.findViewById(R.id.confirm_requestFriends_listview_button);
        Button bdecline = (Button)v.findViewById(R.id.decline_requestFriends_listview_button2);

        //ivw.setImageResource(imagesId[position]);
        ivw.setImageResource(R.drawable.icons8_person_48);
        tvw1.setText(names.get(position));
        bconfirm.setBackgroundColor(Integer.parseInt(context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("newColor","None")));
        bdecline.setBackgroundColor(Integer.parseInt(context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("newColor","None")));

        bconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendGetAnswerRequest spar = new SendGetAnswerRequest();
                try {
                    String response = spar.execute(
                            context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("AppUrl","None")
                            ,context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("Token","None")
                            ,context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("UserId","None")
                            ,String.valueOf(ids.get(position)),"1")
                            .get();
                    response = response.substring(1,response.length()-2);
                    if(response.equals("OK")){
                        Toast.makeText(context, "a request was confirmed", Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent startIntent = new Intent(context, FriendsActivity.class);
                context.startActivity(startIntent);
            }

        });
        bdecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendGetAnswerRequest spar = new SendGetAnswerRequest();
                try {
                    String response = spar.execute(
                            context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("AppUrl","None")
                            ,context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("Token","None")
                            ,context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("UserId","None")
                            ,String.valueOf(ids.get(position)),"0")
                            .get();
                    response = response.substring(1,response.length()-2);
                    if(response.equals("OK")){
                        Toast.makeText(context, "a request was not confirmed", Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent startIntent = new Intent(context, FriendsActivity.class);
                context.startActivity(startIntent);
            }
        });

        return v;
    }
}
