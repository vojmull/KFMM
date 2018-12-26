package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MessagesActivity extends AppCompatActivity {

    ListView messages_listview;
    Integer[] imagesId={R.drawable.ic_launcher_background,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp};
    String[] names={"Pepa","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš"};
    String[] surnames={"Novak","Okurka","Okurka","Okurka","Okurka","Okurka","Okurka"};
    String[] messages={"ahoj pepíku","ahoj pepíku","ahoj pepíku","ahoj pepíku","ahoj pepíku","ahoj pepíku","ahoj pepíku"};
    String[] times={"6:06","6:06","6:06","6:06","6:06","6:06","6:06"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messages_listview=(ListView)findViewById(R.id.messages_listview);
        CustomListView_messages customListView_messages = new CustomListView_messages(this,surnames,names,imagesId,messages,times);
        messages_listview.setAdapter(customListView_messages);

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
    };
}


