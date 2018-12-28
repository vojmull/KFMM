package com.sssvt_prg.tomas.kfm_messenger;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestManager extends AsyncTask<String, Integer, String> {

    private static final String restApiUrl = "http://10.0.2.2:4281";

    private String SendRequest() throws IOException {
        URL url = new URL(restApiUrl + "/api/newtoken/user");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        /*muj add*/
        //conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
        conn.setRequestProperty("Accept","*/*");

        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        conn.setRequestMethod("POST");

        OutputStreamWriter requestWriter = new OutputStreamWriter(conn.getOutputStream());
        requestWriter.write("Email=a@a.a&Password=heslo");
        requestWriter.flush();
        requestWriter.close();

        InputStreamReader inputReader = new InputStreamReader(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        String line = "";

        StringBuilder sb = new StringBuilder();

        while((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\n");
        }

        inputReader.close();
        bufferedReader.close();

        return sb.toString();
    }

    @Override
    protected String doInBackground(String... args){
        try {
            return this.SendRequest();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
