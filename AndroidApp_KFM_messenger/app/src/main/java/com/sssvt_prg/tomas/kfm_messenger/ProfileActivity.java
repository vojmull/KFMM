package com.sssvt_prg.tomas.kfm_messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_messages:
                        Intent intent1 = new Intent(ProfileActivity.this, MessagesActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_profile:
                        break;
                    case R.id.navigation_friends:
                        Intent inten3 = new Intent(ProfileActivity.this, FriendsActivity.class);
                        startActivity(inten3);
                        break;
                    case R.id.navigation_settings:
                        Intent inten4 = new Intent(ProfileActivity.this, SettingsActivity.class);
                        startActivity(inten4);
                        break;
                }
                return false;
            }
        });
    }

}