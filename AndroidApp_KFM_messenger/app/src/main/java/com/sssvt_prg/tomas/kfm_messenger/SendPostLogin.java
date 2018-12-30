package com.sssvt_prg.tomas.kfm_messenger;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SendPostLogin extends AsyncTask<String, Integer, String> {

    public String sendPost(String args)
    {
        String apiurl = "http://10.0.1.13:45455//api/newtoken/user";//"http://10.0.2.2:4281/api/newtoken/user";
        String response = "";
        try {
            URL url= new URL(apiurl);;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DataOutputStream printout;
            DataInputStream input;
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            printout = new DataOutputStream(conn.getOutputStream());
            byte[] data=args.getBytes("UTF-8");
            printout.write(data);
            printout.flush();
            printout.close();

            int responseCode =conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    @Override
    protected String doInBackground(String... strings) {
        return this.sendPost(strings[0]);//"Email=a@a.a&Password=heslo");
    }
}
