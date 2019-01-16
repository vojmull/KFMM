package com.sssvt_prg.tomas.kfm_messenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ManageFriendsActivity extends AppCompatActivity {

    ListView manageFriends_listview;
    Integer[] imagesId={R.drawable.ic_launcher_background,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp};
    List<String> names = new ArrayList<String>();//={"Pepa","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš"};
    List<String> surnames = new ArrayList<String>();//={"Novak","Okurka","Okurka","Okurka","Okurka","Okurka","Okurka"};
    //String[] names ={"Pepa","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš"};
    //String[] surnames ={"Novak","Okurka","Okurka","Okurka","Okurka","Okurka","Okurka"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friends);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Button search_manageFriends_button =(Button)findViewById(R.id.search_manageFriends_button);
        search_manageFriends_button.setBackgroundColor(LoginActivity.newColor);
        EditText name_manageFriends_editText = (EditText) findViewById(R.id.name_manageFriends_editText);


        search_manageFriends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_manageFriends_editText.getText() + "";

                names = new ArrayList<>();
                surnames = new ArrayList<>();

                try {
                    String response = new SendGetAllUsers().execute().get();
                    response = response.substring(1, response.length() - 1);
                    response = response.replace("\\", "");
                    JSONArray jArray = null;
                    try {
                        jArray = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert jArray != null;
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject = jArray.getJSONObject(i);

                            if ((jsonObject.optString("Name") + " " + jsonObject.optString("Surname")).equals(name) || name.equals("")) {
                                names.add(jsonObject.optString("Name"));
                                surnames.add(jsonObject.optString("Surname"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                manageFriends_listview = (ListView) findViewById(R.id.manageFriends_listView);
                CustomListView_manageFriends2 customListView_manageFriends2 = new CustomListView_manageFriends2(ManageFriendsActivity.this, surnames, names, imagesId);
                manageFriends_listview.setAdapter(customListView_manageFriends2);
            }

        });

        names = new ArrayList<>();
        surnames= new ArrayList<>();

        try {
            String response = new SendGetAllUsers().execute().get();
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

                    names.add(jsonObject.optString("Name"));
                    surnames.add(jsonObject.optString("Surname"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        manageFriends_listview=(ListView)findViewById(R.id.manageFriends_listView);
        CustomListView_manageFriends2 customListView_manageFriends2 = new CustomListView_manageFriends2(this,surnames,names,imagesId);
        manageFriends_listview.setAdapter(customListView_manageFriends2);
    }
}
