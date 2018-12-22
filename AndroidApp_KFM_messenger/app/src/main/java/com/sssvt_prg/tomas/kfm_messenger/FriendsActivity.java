package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_messages:
                        Intent intent1 = new Intent(FriendsActivity.this, MessagesActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_profile:
                        Intent intent2 = new Intent(FriendsActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_friends:
                        break;
                    case R.id.navigation_settings:
                        Intent intent4 = new Intent(FriendsActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    };
}
