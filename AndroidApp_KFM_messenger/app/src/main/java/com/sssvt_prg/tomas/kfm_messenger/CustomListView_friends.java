package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomListView_friends extends ArrayAdapter<String> {

    //private Integer[] imagesId;
    private List<Integer> imagesId;
    private List<String> names;
    private List<String> surnames;
    private List<Integer> ids;
    private List<Boolean> online;

    private ListView friends_listview;
    //private String [] names;
    //private String []surnames;

    private Activity context;
    public CustomListView_friends(@NonNull Context context, List<String> surnames,List<String> names,List<Integer> imagesId,List<Integer> ids,List<Boolean> online){
        super(context,R.layout.friends_litview_detail,names);

        this.context = (Activity) context;
        this.names = names;
        this.surnames=surnames;
        this.imagesId=imagesId;
        this.ids=ids;
        this.online=online;



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
        //viewHolder.ivw.setImageResource(imagesId.get(position));
        viewHolder.ivw.setImageResource(R.drawable.ic_home_black_24dp);
        viewHolder.tvw1.setText(names.get(position));
        viewHolder.tvw2.setText(surnames.get(position));
        if(online.get(position)== true){
            viewHolder.tvw3.setText("online");
            viewHolder.tvw3.setTextColor(Color.rgb(0,255,0));
        }
        else
        {
            viewHolder.tvw3.setText("offline");
            viewHolder.tvw3.setTextColor(Color.rgb(255,0,0));
        }
        //viewHolder.tvw1.setText(names[position]);
        //viewHolder.tvw2.setText(surnames[position]);
        viewHolder.bchat.setBackgroundColor(Integer.parseInt(context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("newColor","None")));
        viewHolder.bchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = context.getSharedPreferences("global", Context.MODE_PRIVATE);
                try {
                    SendPostStartNewConversation spsnc = new SendPostStartNewConversation();
                    String response = spsnc.execute("=Ahoj, chceš si se mnou povídat?-"
                            +sp.getString("AppUrl","None")
                            +"-"+sp.getString("Token","None")
                            +"-"+sp.getString("UserId","None")
                            +"-"+ids.get(position)
                    ).get();

                    response = response.substring(1,response.length()-2);
                    if(response.equals("OK")){
                        Toast.makeText(context, "a friend was succesfully removed", Toast.LENGTH_LONG).show();

                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent startIntent = new Intent(context, MessagesActivity.class);
                //pass informations to anther activity later
                context.startActivity(startIntent);
            }
        });
        viewHolder.bdel.setBackgroundColor(Integer.parseInt(context.getSharedPreferences("global",Context.MODE_PRIVATE).getString("newColor","None")));
        viewHolder.bdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = context.getSharedPreferences("global", Context.MODE_PRIVATE);
                try {
                    String response = new SendGetRemoveFriend().execute(String.valueOf(ids.get(position)),sp.getString("AppUrl","None"),
                            sp.getString("Token","None"),
                            sp.getString("UserId","None")).get();
                    response = response.substring(1,response.length()-2);
                    if(response.equals("OK")){
                        Toast.makeText(context, "a friend was succesfully removed", Toast.LENGTH_LONG).show();

                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent startIntent = new Intent(context, FriendsActivity.class);
                //pass informations to anther activity later
                context.startActivity(startIntent);
            }
        });



        return r;
    }
    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView ivw;
        Button bchat;
        Button bdel;

        ViewHolder(View v){
        tvw1 = (TextView) v.findViewById(R.id.name_friends_textView);
        tvw2 = (TextView)v.findViewById(R.id.surname_friends_textView);
        tvw3 = (TextView)v.findViewById(R.id.online_textView);
        ivw = (ImageView)v.findViewById(R.id.img_friends_imageView);
        bchat = (Button)v.findViewById(R.id.chat_friends_listview_button);
        bdel = (Button)v.findViewById(R.id.delete_friends_listview_button2);

        }
    }
}
