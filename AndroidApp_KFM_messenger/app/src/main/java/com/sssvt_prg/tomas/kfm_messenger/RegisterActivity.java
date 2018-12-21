package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button login_register_button = (Button)findViewById(R.id.login_register_button);
        login_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),LoginActivity.class);
                //pass informations to anther activity later
                startActivity(startIntent);
            }
        });
    }
}
