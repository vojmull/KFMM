package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConversationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(LoginActivity.newColor);

        Button reload_conversation_button = (Button) findViewById(R.id.reload_conversation_button);
        reload_conversation_button.setTextColor(LoginActivity.newColor);

        ListView conversation_listView;

        final List<String> conversations = new ArrayList<String>();
        List<Boolean> seen = new ArrayList<Boolean>();
        List<Boolean> delievered = new ArrayList<Boolean>();
        List<String> times = new ArrayList<String>();
        List<String> authors = new ArrayList<String>();



        Intent in = getIntent();
        int index = in.getIntExtra("ItemIndex",-1);

        final String idMessage = MessagesActivity.conversationsId.get(index);

        TextView messages_user = (TextView) findViewById(R.id.userName_textView);

        //messages_user.setText(MessagesActivity.names.get(index)+" "+MessagesActivity.surnames.get(index));
        messages_user.setText(MessagesActivity.chatnames.get(index));



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
                    seen.add(jsonObject.optBoolean("Seen"));
                    delievered.add(jsonObject.optBoolean("Delievered"));
                    times.add(jsonObject.optString("TimeSent"));
                    authors.add(jsonObject.optString("IdAuthor"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        conversation_listView = (ListView) findViewById(R.id.conversation_listView);
        CustomListView_conversations customListView_conversations = new CustomListView_conversations(this,conversations,delievered,seen,times,authors);
        conversation_listView.setAdapter(customListView_conversations);




        Button send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText messsage_edittext = (EditText) findViewById(R.id.message_editText);
                String message = messsage_edittext.getText() + "";

                SendPostConversation spc = new SendPostConversation();

                try {
                    String response = spc.execute("="+message+"/"+idMessage).get();
                    messsage_edittext.setText("");

                    ListView conversation_listView;

                    List<String> conversations = new ArrayList<String>();
                    List<Boolean> seen = new ArrayList<Boolean>();
                    List<Boolean> delievered = new ArrayList<Boolean>();
                    List<String> times = new ArrayList<String>();
                    List<String> authors = new ArrayList<String>();

                    try {
                        String response2 = new SendGetConversations().execute(idMessage).get();
                        response2 = response2.substring(1,response2.length()-1);
                        response2 = response2.replace("\\","");
                        JSONArray jArray = null;

                        String response3 = new SendGetConfirmConversationRead().execute(idMessage).get();
                        try {
                            jArray = new JSONArray(response2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            for(int i=0;i<jArray.length();i++)
                            {
                                JSONObject jsonObject = jArray.getJSONObject(i);
                                conversations.add(jsonObject.optString("Content"));
                                seen.add(jsonObject.optBoolean("Seen"));
                                delievered.add(jsonObject.optBoolean("Delievered"));
                                times.add(jsonObject.optString("TimeSent"));
                                authors.add(jsonObject.optString("IdAuthor"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    conversation_listView = (ListView) findViewById(R.id.conversation_listView);
                    CustomListView_conversations customListView_conversations = new CustomListView_conversations(ConversationActivity.this,conversations,delievered,seen,times,authors);
                    conversation_listView.setAdapter(customListView_conversations);


                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Button reload_conversation_button = (Button) findViewById(R.id.reload_conversation_button);
        reload_conversation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListView conversation_listView;

                List<String> conversations = new ArrayList<String>();
                List<Boolean> seen = new ArrayList<Boolean>();
                List<Boolean> delievered = new ArrayList<Boolean>();
                List<String> times = new ArrayList<String>();
                List<String> authors = new ArrayList<String>();

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
                            seen.add(jsonObject.optBoolean("Seen"));
                            delievered.add(jsonObject.optBoolean("Delievered"));
                            times.add(jsonObject.optString("TimeSent"));
                            authors.add(jsonObject.optString("IdAuthor"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                conversation_listView = (ListView) findViewById(R.id.conversation_listView);
                CustomListView_conversations customListView_conversations = new CustomListView_conversations(ConversationActivity.this,conversations,delievered,seen,times,authors);
                conversation_listView.setAdapter(customListView_conversations);
            }
        });
    }
}
