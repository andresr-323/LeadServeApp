package com.example.leadserve;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Loading extends AppCompatActivity {
    String ID;
    public static ArrayList<Student> Students = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent i = getIntent();
        ID = i.getExtras().getString("ID");

        downloadstudentJSON("http://52.45.183.203:80/Android/mStud.php");
    }
    @Override
    public void onBackPressed() {
    }

    //https://www.skysilk.com/blog/2018/how-to-connect-an-android-app-to-a-mysql-database/ Website im using rn??????????
    private void downloadstudentJSON(final String urlWebService) {

        class DownloadStudentJSON extends AsyncTask<Void, Void, String> {
            DownloadStudentJSON(){
                Context ctx = Loading.this;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadStudents(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadStudentJSON getJSON = new DownloadStudentJSON();
        getJSON.execute();
    }

    private void loadStudents(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] stocks = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Student s = new Student();
            s.setAdvisor(obj.getString("advisor"));
            s.setCampus(obj.getString("campus"));
            s.setEmail(obj.getString("email"));
            s.setExpectedGrad(obj.getString("expectedGrad"));
            s.setHometown(obj.getString("hometown"));
            s.setID(obj.getInt("studentID"));
            s.setMajor(obj.getString("major"));
            s.setName(obj.getString("firstName") + " " + obj.getString("lastName"));
            s.setPrefName(obj.getString("preferredName"));
            s.setTierNumber(obj.getString("tierNumber"));
            s.setTshirtSize(obj.getString("tshirtSize"));
            Students.add(s);
        }
        Intent i = new Intent(Loading.this, homepage.class);
        Bundle args = new Bundle();
        args.putSerializable("STUD", Students);
        i.putExtra("STUDBUNDLE",args);
        i.putExtra("ID", ID);
        startActivity(i);
        finish();
    }
}


