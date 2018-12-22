package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

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
                        Intent intent3 = new Intent(MessagesActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_settings:
                        Intent inten4 = new Intent(MessagesActivity.this, ProfileActivity.class);
                        startActivity(inten4);
                        break;
                }
            return false;
            }
        });
    };
}


