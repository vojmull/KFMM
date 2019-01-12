package com.sssvt_prg.tomas.kfm_messenger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button register_register_button=(Button)findViewById(R.id.register_register_button);
        register_register_button.setBackgroundColor(LoginActivity.newColor);
        register_register_button.setTextColor(Color.WHITE);
        TextView register_register_textView=(TextView)findViewById(R.id.register_register_textView);
        register_register_textView.setTextColor(LoginActivity.newColor);
        Button login_register_button=(Button)findViewById(R.id.login_register_button);
        login_register_button.setTextColor(LoginActivity.newColor);

        final EditText email_register_edittext = (EditText) findViewById(R.id.email_register_editText);
        final EditText password_register_edittext = (EditText) findViewById(R.id.password_register_editText);
        final EditText name_register_edittext = (EditText) findViewById(R.id.name_register_editText);
        final EditText surname_register_edittext = (EditText) findViewById(R.id.surname_register_editText);

        //Button login_register_button = (Button)findViewById(R.id.login_register_button);
        login_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),LoginActivity.class);
                //pass informations to anther activity later
                startActivity(startIntent);
            }
        });

        //Button register_register_button = (Button) findViewById(R.id.register_register_button);
        register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = email_register_edittext.getText() + "";
                String password = password_register_edittext.getText() + "";
                String name = name_register_edittext.getText() + "";
                String surname = surname_register_edittext.getText() + "";

                if (email.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (surname.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please fill surname", Toast.LENGTH_SHORT).show();
                    return;
                }

                SendPostRegister spr = new SendPostRegister();
                try {
                    String response = spr.execute("Email="+email+"&Password="+password+"&Name="+name+"&Surname="+surname).get();
                    response = response.substring(1,response.length()-1);

                    if(response.equals("ConnectionWithDatabaseProblem")){
                        Toast.makeText(RegisterActivity.this, "Connection with database problem", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.equals("WrongEmail")){
                        Toast.makeText(RegisterActivity.this, "Try different email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.equals("OK")){
                        Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        Intent startIntent = new Intent(getApplicationContext(),LoginActivity.class);
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

