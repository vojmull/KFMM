package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessagesActivity extends AppCompatActivity {

    ListView messages_listview;

    public static List <String> names = new ArrayList<String>();
    public static List <String> surnames = new ArrayList<String>();
    List <String> messages= new ArrayList<String>();
    List <String> times = new ArrayList<String>();
    List <Integer> imagesId = new ArrayList<Integer>();
    public static List <String> conversationsId = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        try {
            String response = new SendGetMessages().execute().get();
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

                    if(jsonObject.optString("LastMessage").length() >60)
                        messages.add(jsonObject.optString("LastMessage").substring(0,60)+"...");
                    else
                        messages.add(jsonObject.optString("LastMessage"));

                    names.add(jsonObject.optString("Name"));
                    surnames.add(jsonObject.optString("Surname"));
                    times.add(jsonObject.optString("LastMessageSent"));
                    imagesId.add(R.drawable.ic_launcher_background);
                    conversationsId.add(jsonObject.optString("Id"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        messages_listview = (ListView) findViewById(R.id.messages_listview);
        CustomListView_messages customListView_messages = new CustomListView_messages(this, surnames, names, imagesId, messages, times);
        messages_listview.setAdapter(customListView_messages);

        messages_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent MessagesDetailIntent = new Intent(MessagesActivity.this,ConversationActivity.class);
                MessagesDetailIntent.putExtra("ItemIndex",position);
                startActivity(MessagesDetailIntent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_messages:
                            break;
                        case R.id.navigation_profile:
                            Intent intent2 = new Intent(MessagesActivity.this, ProfileActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.navigation_friends:
                            Intent intent3 = new Intent(MessagesActivity.this, FriendsActivity.class);
                            startActivity(intent3);
                            break;
                        case R.id.navigation_settings:
                            Intent inten4 = new Intent(MessagesActivity.this, SettingsActivity.class);
                            startActivity(inten4);
                            break;
                    }
                    return false;
                }
            });
        }

}






