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

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String AppUrl = "http://192.168.50.75:45455";
    public static String Token = "";
    public static String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email_login_edittext = (EditText) findViewById(R.id.email_login_editText);
        final EditText password_login_edittext = (EditText) findViewById(R.id.password_login_editText);

        Button register_login_button = (Button) findViewById(R.id.register_login_button);
        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                //pass informations to anther activity later
                startActivity(startIntent);
            }
        });

        Button login_login_button = (Button) findViewById(R.id.login_login_button);
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = email_login_edittext.getText() + "";
                String password = password_login_edittext.getText() + "";
                if (email.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please fill email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please fill password", Toast.LENGTH_SHORT).show();
                    return;
                }

                SendPostLogin spl = new SendPostLogin();
                try {
                    String response = spl.execute("Email="+email+"&Password="+password).get();
                    response = response.substring(1,response.length()-1);
                    if(response.equals("BadPassword")){
                        Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.equals("BadUserName")){
                        Toast.makeText(LoginActivity.this, "Wrong email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(response.equals("ConnectionWithDatabaseProblem")){
                        Toast.makeText(LoginActivity.this, "Connection with database problem", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    /*
                    SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("token", response);
                    editor.commit();
                    */

                    String segments[] = response.split("/");
                    Token = segments[0];
                    UserID = segments[1];

                    Intent startIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(startIntent);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}


