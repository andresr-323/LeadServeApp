package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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
    String myName;
    ArrayList<Student> Students= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        downloadStudentJSON("http://52.45.183.203:80/mStud.php");
//        downloadEventsJSON("http://52.45.183.203:80/mStud.php");
    }
    @Override
    public void onBackPressed() {
    }





    private void downloadStudentJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

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
//                    Intent intent = new Intent(Loading.this, homepage.class);
//                    //intent.putExtra("Name", myName);
//                    //intent.putextra
////                    intent.putExtra("divisions", divisions);
////                    intent.putExtra("completed", completed);
//
//                    Bundle b = new Bundle();
//    // putting questions list into the bundle .. as key value pair.
//    // so you can retrieve the arrayList with this key
//                    b.putSerializable("stud", Students);
//                    intent.putExtras(b);
//                    startActivity(intent);
//                    //System.out.println("end of loading");
//                    finish();
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
//        DownloadJSON getJSON = new DownloadJSON();
//        getJSON.execute();
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
            s.setTierNumber(obj.getInt("tierNumber"));
            s.setTshirtSize(obj.getString("tshirtSize"));
            Students.add(s);
        }

        Intent intent = new Intent(Loading.this, homepage.class);
        Bundle b = new Bundle();
        b.putSerializable("stud", Students);
        intent.putExtras(b);
        startActivity(intent);
        //System.out.println("end of loading");
        finish();
    }

//    private void downloadEventsJSON(final String urlWebService) {
//        class DownloadJSON extends AsyncTask<Void, Void, String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//                try {
//                    loadEvents(s);
//
////                    Intent intent = new Intent(Loading.this, homepage.class);
////                    intent.putExtra("Name", myName);
////                    //intent.putextra
//////                    intent.putExtra("divisions", divisions);
//////                    intent.putExtra("completed", completed);
////                    startActivity(intent);
////                    System.out.println("end of loading");
////                    finish();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                    URL url = new URL(urlWebService);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//                    return sb.toString().trim();
//                } catch (Exception e) {
//                    return null;
//                }
//            }
//        }
//        DownloadJSON getJSON = new DownloadJSON();
//        getJSON.execute();
//    }
//
//    private void loadEvents(String json) throws JSONException {
//        JSONArray jsonArray = new JSONArray(json);
//        String[] stocks = new String[jsonArray.length()];
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject obj = jsonArray.getJSONObject(i);
//            Student s = new Student();
//            s.setAdvisor(obj.getString("advisor"));
//            s.setCampus(obj.getString("campus"));
//            s.setEmail(obj.getString("email"));
//            s.setExpectedGrad(obj.getString("expectedGrad"));
//            s.setHometown(obj.getString("hometown"));
//            s.setID(obj.getInt("studentID"));
//            s.setMajor(obj.getString("major"));
//            s.setName(obj.getString("firstName") + " " + obj.getString("lastName"));
//            s.setPrefName(obj.getString("preferredName"));
//            s.setTierNumber(obj.getInt("tierNumber"));
//            s.setTshirtSize(obj.getString("tshirtSize"));
//            //Students.add(s);
//        }
//    }
}


