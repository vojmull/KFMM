package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

public class FriendsActivity extends AppCompatActivity {

    ListView friends_listview;
    Integer[] imagesId={R.drawable.ic_launcher_background,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp};
    String[] names={"Pepa","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš","Tomáš"};
    String[] surnames={"Novak","Okurka","Okurka","Okurka","Okurka","Okurka","Okurka"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(LoginActivity.newColor));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(LoginActivity.newColor);

        Button add_friends_button=(Button)findViewById(R.id.add_friends_button);
        add_friends_button.setBackgroundColor(LoginActivity.newColor);
        //Button chat_friends_listview_button=(Button)findViewById(R.id.chat_friends_listview_button);
        //chat_friends_listview_button.setBackgroundColor(LoginActivity.newColor);


        friends_listview=(ListView)findViewById(R.id.friends_listview);
        CustomListView_friends customListView_friends = new CustomListView_friends(this,surnames,names,imagesId);
        friends_listview.setAdapter(customListView_friends);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(ColorStateList.valueOf(LoginActivity.newColor));
        navigation.setItemTextColor(ColorStateList.valueOf(LoginActivity.newColor));
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
