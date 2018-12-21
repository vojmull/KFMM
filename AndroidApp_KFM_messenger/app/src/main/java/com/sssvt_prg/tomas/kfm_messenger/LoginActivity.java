package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button register_login_button = (Button)findViewById(R.id.register_login_button);
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                //pass informations to anther activity later
                startActivity(startIntent);
            }
        });
    }
}
