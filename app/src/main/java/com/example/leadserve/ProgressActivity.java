package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgressActivity extends AppCompatActivity {
    private String ID;
    private String tier;
    private final String URL = "http://52.45.183.203:80/";
    private tierOne t1 = new tierOne();
    private tierTwo t2 = new tierTwo();
    private tierThree t3 = new tierThree();
    ListView lv;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Progress");
        toolbar.setTitleTextColor(getResources().getColor(R.color.SLgold));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        ID = intent.getExtras().getString("ID");
        tier = intent.getExtras().getString("tier");

        downloadprogressJSON(URL+"Android/mProg1.php",URL+"Android/mProg2.php" ,URL+"Android/mProg3.php" , ID);
    }


    private void downloadprogressJSON(final String urlWebService,final String urlWebService2 ,final String urlWebService3 , final String ID) {

        class DownloadProgressJSON extends AsyncTask<Void, Void, String[]> {
            DownloadProgressJSON(){
                Context ctx = ProgressActivity.this;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String[] s) {
                super.onPostExecute(s);

                try {
                    loadTierOne(s[0]);
                    loadTierTwo(s[1]);
                    loadTierThree(s[2]);
//                    if(tier.equals("1"))
//                        loadTierOne(s);
//                    else if(tier.equals("2"))
//                        loadTierTwo(s);
//                    else if(tier.equals("3"))
//                        loadTierThree(s);
//                    else
//                        Log.d("error in loadtier: ", "tier not found");

                } catch (JSONException e) {
                    Log.d("error in loadtier: ", "tier not found");
                    e.printStackTrace();
                }
            }

            @Override
            protected String[] doInBackground(Void... voids) {
                try {
                    String[] j = new String[3];
                    String j1, j2, j3;

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
                    j1 = sb.toString().trim();

                    url = new URL(urlWebService2);
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    sb = new StringBuilder();
                    String json2;
                    while ((json2 = bufferedReader.readLine()) != null) {
                        sb.append(json2 + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    j2 = sb.toString().trim();

                    url = new URL(urlWebService3);
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    outputStream = httpURLConnection.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    post_data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    sb = new StringBuilder();
                    String json3;
                    while ((json3 = bufferedReader.readLine()) != null) {
                        sb.append(json3 + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    j3 = sb.toString().trim();

                    j[0] = j1;
                    j[1] = j2;
                    j[2] = j3;

                    return j;
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
//        ArrayList<tierOne> t = new ArrayList<>();
//        t.add(t1);
//        lv = findViewById(R.id.lvProg);
//        tierAdapter itemsAdapter = new tierAdapter(t, this);
//        lv.setAdapter(itemsAdapter);

    }

    private void loadTierTwo(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);

        t2.setFivehour(obj.getInt("FiveHourServiceReflection"));
        t2.setLegacyProjectProp(obj.getInt("LegacyProjectProposal"));
        t2.setLEAD2000(obj.getInt("LEAD2000"));
        t2.setShowcase(obj.getInt("Showcase"));

        Log.d("loadt2:", "load done");
//        ArrayList<tierTwo> t = new ArrayList<>();
//        t.add(t2);
//        lv = findViewById(R.id.lvProg);
//        tierTwoAdapter itemsAdapter = new tierTwoAdapter(t, this);
//        lv.setAdapter(itemsAdapter);

    }

    private void loadTierThree(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);

        t3.setLeadershipLegProj(obj.getInt("LeadershipLegacyProject"));
        t3.setLEAD3000(obj.getInt("LEAD3000"));
        t3.setLeadershipPort(obj.getInt("LeadershipPortfolio"));
        t3.setShowcase(obj.getInt("Showcase"));

        Log.d("loadt3:", "load done");
//        ArrayList<tierThree> t = new ArrayList<>();
//        t.add(t3);
//        lv = findViewById(R.id.lvProg);
//        tierThreeAdapter itemsAdapter = new tierThreeAdapter(t, this);
//        lv.setAdapter(itemsAdapter);

        expandableListView = (ExpandableListView) findViewById(R.id.tierExpandableListView);
        expandableListDetail = TierEListDataPump.getData(t1, t2, t3);
        ArrayList<String> s = new ArrayList<String>(expandableListDetail.keySet());
        ArrayList<String> revArrayList = new ArrayList<String>();
        for (int i = s.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(s.get(i));
        }

        expandableListTitle = revArrayList;
        expandableListAdapter = new TierExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        int x = Integer.parseInt(tier)-1;
        expandableListView.expandGroup(x);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //shows which dropdown has been selected and toggled/expanded
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //gives you the list that was uncollapsed
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //gives you the exact item in the collapsed list chosen
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

    }
}
