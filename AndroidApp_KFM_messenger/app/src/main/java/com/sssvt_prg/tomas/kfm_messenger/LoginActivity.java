package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email_login_edittext = (EditText) findViewById(R.id.email_login_editText);
        final EditText password_login_edittext = (EditText)findViewById(R.id.password_login_editText);

        Button register_login_button = (Button)findViewById(R.id.register_login_button);
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                //pass informations to anther activity later
                startActivity(startIntent);
            }
        });

        Button login_login_button = (Button)findViewById(R.id.login_login_button);
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = email_login_edittext.getText()+"";
                String password = password_login_edittext.getText()+"";
                if(email.length()== 0){
                    Toast.makeText(LoginActivity.this, "Please fill email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()== 0){
                    Toast.makeText(LoginActivity.this, "Please fill password", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    String rm;
                    rm = new RequestManager().execute().get();

                    if(rm !=""){
                        Intent startIntent = new Intent(getApplicationContext(),MessagesActivity.class);
                        //pass informations to anther activity later
                        startActivity(startIntent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




            }
        });
    }

}


