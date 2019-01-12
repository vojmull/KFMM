package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    //public static int newColor;// = Color.rgb(253,166,1);//= Color.parseColor("#fda601");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(LoginActivity.newColor));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(LoginActivity.newColor);

        Button changeColor_button = (Button) findViewById(R.id.changeColor_button);
        changeColor_button.setBackgroundColor(LoginActivity.newColor);
        changeColor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ChangeColorActivity.class);
                startActivity(startIntent);
            }
        });

        Button logout_settings_button = (Button) findViewById(R.id.logout_settings_button);
        logout_settings_button.setBackgroundColor(LoginActivity.newColor);
        logout_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.Token = "";
                LoginActivity.UserID = "";
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                //pass informations to anther activity later
                startActivity(startIntent);
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(ColorStateList.valueOf(LoginActivity.newColor));
        navigation.setItemTextColor(ColorStateList.valueOf(LoginActivity.newColor));
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_messages:
                        Intent intent1 = new Intent(SettingsActivity.this, MessagesActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_profile:
                        Intent intent2 = new Intent(SettingsActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_friends:
                        Intent intent3 = new Intent(SettingsActivity.this, FriendsActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_settings:
                        break;
                }
                return false;
            }
        });
    };
}
