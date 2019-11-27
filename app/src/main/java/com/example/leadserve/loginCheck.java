package com.example.leadserve;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class loginCheck extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    private String username;
    private String pass;
    private FirebaseAuth mAuth;
//    private asyncCallBack mCallback;
//
//    public loginCheck(asyncCallBack callback, String x) {
//
//        mCallback = callback;
//    }

    loginCheck(Context ctx, String username, String pass) {
        context = ctx;
        this.username = username;
        this.pass = pass;

    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://52.45.183.203:80/Android/mlog.php";
        if(type.equals("login")) {
            try {
                String email = params[1];
                String pass = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Attempt Failed";
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status:");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onPostExecute(String result) {
        String reg = "[0-9]+";

        String s[] = result.split(" ");

        if(s[0].matches(reg)){
            mAuth.createUserWithEmailAndPassword(username, pass);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mAuth.signInWithEmailAndPassword(username, pass);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(this.context, Loading.class);
            i.putExtra("ID", s[0]);
            i.putExtra("tier", s[1]);
            i.putExtra("Name", s[2]+" "+s[3]);
            i.putExtra("vCode", s[4]);
            this.context.startActivity(i);
        } else if(result.equals("login not successful")){
            alertDialog.setMessage(result);
            alertDialog.show();
        } else if(result.equals("archived")){
            alertDialog.setMessage("Login to website to download certificates!\nApp is only for current in program students.");
            alertDialog.show();
        }else{
            alertDialog.setMessage("ERROR");
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}