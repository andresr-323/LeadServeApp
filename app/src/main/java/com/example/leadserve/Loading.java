package com.example.leadserve;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Loading extends AppCompatActivity {
    String myName;
    public static ArrayList<Student> Students = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        downloadJSON("http://52.45.183.203:80/Android/mStud.php");
    }
    @Override
    public void onBackPressed() {
    }

    //https://www.skysilk.com/blog/2018/how-to-connect-an-android-app-to-a-mysql-database/ Website im using rn??????????
    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    //loadIntoListView(s);
//                    Intent i = new Intent(Loading.this, homepage.class);
//                    startActivity(i);
                    JSONArray jsonArray = new JSONArray(s);     //try moving the function into here?
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Student st = new Student();
                        st.setAdvisor(obj.getString("advisor"));
                        st.setCampus(obj.getString("campus"));
                        st.setEmail(obj.getString("email"));
                        st.setExpectedGrad(obj.getString("expectedGrad"));
                        st.setHometown(obj.getString("hometown"));
                        st.setID(obj.getInt("studentID"));
                        st.setMajor(obj.getString("major"));
                        st.setName(obj.getString("firstName") + " " + obj.getString("lastName"));
                        st.setPrefName(obj.getString("preferredName"));
                        st.setTierNumber(obj.getInt("tierNumber"));
                        st.setTshirtSize(obj.getString("tshirtSize"));
                        Students.add(st);
                    }
                    Intent i = new Intent(Loading.this, homepage.class);
                    startActivity(i);
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
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

//    private void loadIntoListView(String json) throws JSONException {
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
//            Students.add(s);
//        }
//        Intent i = new Intent(Loading.this, homepage.class);
//        startActivity(i);
//        //finish();
//    }
}


