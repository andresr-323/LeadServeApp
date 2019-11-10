package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {
    private String ID;
    private String tier;
    private final String URL = "http://52.45.183.203:80/";
    private tierOne t1 = new tierOne();
    private tierTwo t2 = new tierTwo();
    private tierThree t3 = new tierThree();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        ID = intent.getExtras().getString("ID");
        tier = intent.getExtras().getString("tier");

        downloadprogressJSON(URL+"Android/mProg.php", ID);
    }


    private void downloadprogressJSON(final String urlWebService, final String ID) {

        class DownloadProgressJSON extends AsyncTask<Void, Void, String> {
            DownloadProgressJSON(){
                Context ctx = ProgressActivity.this;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    if(tier.equals("1"))
                        loadTierOne(s);
                    else if(tier.equals("2"))
                        loadTierTwo(s);
                    else if(tier.equals("3"))
                        loadTierThree(s);
                    else
                        Log.d("error in loadtier: ", "tier not found");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadProgressJSON getJSON = new DownloadProgressJSON();
        getJSON.execute();
    }

    private void loadTierOne(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);

        t1.setCoachingProgram(obj.getInt("CoachingProgram"));
        t1.setLEAD1000(obj.getInt("LEAD1000"));
        t1.setShowcase(obj.getInt("Showcase"));

        Log.d("loadt1:", "load done");
        ArrayList<tierOne> t = new ArrayList<>();
        t.add(t1);
        lv = findViewById(R.id.lvProg);
        tierAdapter itemsAdapter = new tierAdapter(t, this);
        lv.setAdapter(itemsAdapter);

    }

    private void loadTierTwo(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);

        t2.setFivehour(obj.getInt("FiveHourServiceReflection"));
        t2.setLegacyProjectProp(obj.getInt("LegacyProjectProposal"));
        t2.setLEAD2000(obj.getInt("LEAD2000"));
        t2.setShowcase(obj.getInt("Showcase"));

        Log.d("loadt2:", "load done");
        ArrayList<tierTwo> t = new ArrayList<>();
        t.add(t2);
        lv = findViewById(R.id.lvProg);
        tierTwoAdapter itemsAdapter = new tierTwoAdapter(t, this);
        lv.setAdapter(itemsAdapter);

    }

    private void loadTierThree(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);

        t3.setLeadershipLegProj(obj.getInt("LeadershipLegacyProject"));
        t3.setLEAD3000(obj.getInt("LEAD3000"));
        t3.setLeadershipPort(obj.getInt("LeadershipPortfolio"));
        t3.setShowcase(obj.getInt("Showcase"));

        Log.d("loadt3:", "load done");
        ArrayList<tierThree> t = new ArrayList<>();
        t.add(t3);
        lv = findViewById(R.id.lvProg);
        tierThreeAdapter itemsAdapter = new tierThreeAdapter(t, this);
        lv.setAdapter(itemsAdapter);

    }
}
