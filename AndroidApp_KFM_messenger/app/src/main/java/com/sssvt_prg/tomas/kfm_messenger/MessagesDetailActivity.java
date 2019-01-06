package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessagesDetailActivity extends AppCompatActivity {

    ListView conversation_listView;

    List<String> conversations = new ArrayList<String>();
    List<String> seen = new ArrayList<String>();
    List<String> delievered = new ArrayList<String>();
    List<String> times = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_detail);

        Intent in = getIntent();
        int index = in.getIntExtra("ItemIndex",-1);

        String idMessage = MessagesActivity.conversationsId.get(index);

        TextView messages_user = (TextView) findViewById(R.id.user_messagesDetail_textView);
        messages_user.setText(MessagesActivity.names.get(index)+" "+MessagesActivity.surnames.get(index));


        try {
            String response = new SendGetConversations().execute(idMessage).get();
            response = response.substring(1,response.length()-1);
            response = response.replace("\\","");
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                for(int i=0;i<jArray.length();i++)
                {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    conversations.add(jsonObject.optString("Content"));
                    seen.add(jsonObject.optString("Seen"));
                    delievered.add(jsonObject.optString("Delievered"));
                    times.add(jsonObject.optString("TimeSent"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        conversation_listView = (ListView)findViewById(R.id.conversation_listView);
        CustomListView_conversations conversations_adapter = new CustomListView_conversations(this,conversations,delievered,seen,times);
        conversation_listView.setAdapter(conversations_adapter);

    }

}
