package com.example.leadserve;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

public class Loading extends AppCompatActivity {
    private String ID;
    private String tier;
    private String name;
    private String vCode;
    public Intent i;
    public static ArrayList<Student> Students = new ArrayList<>();
    public static ArrayList<Event> Events = new ArrayList<>();
    private final String URL = "http://52.45.183.203:80/";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ProgressBar spinner = findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.SLgold), android.graphics.PorterDuff.Mode.MULTIPLY);

        Intent ia = getIntent();
        ID = ia.getExtras().getString("ID");
        tier = ia.getExtras().getString("tier");
        name = ia.getExtras().getString("Name");
        vCode = ia.getExtras().getString("vCode");
        i = new Intent(Loading.this, homepage.class);

        downloadstudentJSON(URL+"Android/mStud.php?id="+vCode, URL+"Android/mEven.php?id="+vCode);
    }

    @Override
    public void onBackPressed() {
    }

    private void downloadstudentJSON(final String urlWebService, final String urlWebService2) {

        class DownloadStudentJSON extends AsyncTask<Void, Void, String[]> {
            DownloadStudentJSON(){
                Context ctx = Loading.this;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String[] s) {
                super.onPostExecute(s);
                try {
                    String studs = s[0];
                    String events = s[1];
                    loadStudents(studs);
                    loadEvents(events);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String[] doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    URL url2 = new URL(urlWebService2);
                    HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
                    StringBuilder sb2 = new StringBuilder();
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
                    String json2;
                    while ((json2 = bufferedReader2.readLine()) != null) {
                        sb2.append(json2 + "\n");
                    }

                    String[] arr = new String[2];
                    arr[0] = sb.toString().trim();
                    arr[1] = sb2.toString().trim();
                    return arr;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadStudentJSON getJSON = new DownloadStudentJSON();
        getJSON.execute();
    }

    private void loadEvents(String json2) throws JSONException {
        JSONArray jsonArray = new JSONArray(json2);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Event e = new Event();
            e.setEventID(obj.getInt("eventID"));
            e.setTitle(obj.getString("title"));
            e.setTiers(obj.getString("tierNumber"));
            e.setDescription(obj.getString("description"));
            e.setLocation(obj.getString("location"));
            e.setDate(obj.getString("date"));
            e.setTime(obj.getString("time"));
            e.setImgPath(obj.getString("pathName"));
            e.setCampus(obj.getString("campus"));
            Events.add(e);
        }
        //Log.d("loadEvents:", "load done");
        Bundle args = new Bundle();
        args.putSerializable("EVEN", Events);
        i.putExtra("EVENTBUNDLE",args);
        i.putExtra("ID", ID);
        i.putExtra("tier", tier);
        i.putExtra("name", name);
        i.putExtra("vCode", vCode);
        startActivity(i);
        finish();
    }

    private void loadStudents(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
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
       // Log.d("loadStudents:", "load done");
        Bundle args = new Bundle();
        args.putSerializable("STUD", Students);
        i.putExtra("STUDBUNDLE",args);
    }
}


