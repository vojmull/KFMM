package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class friendRequestsActivity extends AppCompatActivity {
    ListView requestFriends_listview;
    Integer[] imagesId={R.drawable.ic_launcher_background,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp};
    List<String> names = new ArrayList<String>();//={"Pepa","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš"};
    List<Integer> ids = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Integer.parseInt(this.getSharedPreferences("global",Context.MODE_PRIVATE).getString("newColor","None")));

        SharedPreferences sp = getSharedPreferences("global", Context.MODE_PRIVATE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        names = new ArrayList<>();
        ids = new ArrayList<Integer>();

        try {
            String response = new SendGetAllRequests().execute(
                    sp.getString("AppUrl","None"),
                    sp.getString("Token","None"),
                    sp.getString("UserId","None"))
                    .get();
            response = response.substring(1,response.length()-1);
            response = response.replace("\\","");
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                assert jArray != null;
                for(int i = 0; i<jArray.length(); i++)
                {
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    names.add(jsonObject.optString("RequestorName"));
                    ids.add(jsonObject.optInt("IdUserRequestor"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


            requestFriends_listview = (ListView) findViewById(R.id.requests_listView);
            CustomListView_requestFriends customListView_requestFriends = new CustomListView_requestFriends(this, names, imagesId, ids);
            requestFriends_listview.setAdapter(customListView_requestFriends);

    }
}
