package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        EditText name_profile_editText = (EditText) findViewById(R.id.name_profile_editText);
        EditText surname_profile_editText = (EditText) findViewById(R.id.surname_profile_editText);
        EditText date_profile_editText = (EditText) findViewById(R.id.date_profile_editText);
        EditText email_profile_editText = (EditText) findViewById(R.id.email_profile_editText);
        EditText phone_profile_editText = (EditText) findViewById(R.id.phone_profile_editText);

        try {
            String response = new SendGetProfile().execute().get();
            response = response.substring(1,response.length()-1);
            response = response.replace("\\","");
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert jObject != null;

            name_profile_editText.setText(jObject.optString("Name"));
            surname_profile_editText.setText(jObject.optString("Surname"));
            date_profile_editText.setText(jObject.optString("Birthdate"));
            email_profile_editText.setText(jObject.optString("Email"));
            phone_profile_editText.setText(jObject.optString("Phone"));


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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