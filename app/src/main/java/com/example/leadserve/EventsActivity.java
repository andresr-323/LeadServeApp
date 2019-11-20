package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

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

public class EventsActivity extends AppCompatActivity {
    String ID;
    String tier;
    private ArrayList<Event> Events = new ArrayList();
    private ArrayList<Event> relEvents = new ArrayList<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Events");
        toolbar.setTitleTextColor(getResources().getColor(R.color.SLgold));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        ID = intent.getExtras().getString("ID");

        Bundle studs = intent.getBundleExtra("EVENTBUNDLE");
        Events = (ArrayList<Event>) studs.getSerializable("EVENT");

        tier = intent.getExtras().getString("tier");

//        ListView lv = findViewById(R.id.EventList);
//        EventAdapter ed = new EventAdapter(Events, this);
//        lv.setAdapter(ed);
        //https://www.journaldev.com/9942/android-expandablelistview-example-tutorial used
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        if(expandableListDetail == null) {
            expandableListDetail = ExpandableListDataPump.getData(Events);
        }
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //shows which dropdown has been selected and toggled/expanded
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //gives you the list that was uncollapsed
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //gives you the exact item in the collapsed list chosen
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
                int cnt = 0;
                Event sel = new Event();
                for(Event e: Events){
                    //selects the event clicked
                    if(e.getTitle().equals(expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition))){
                        cnt++;
                        sel = e;
                    }
                }
                if(cnt == 1){
                    Intent i = new Intent(EventsActivity.this, DisplayEventActivity.class);
                    i.putExtra("event",sel);
                    startActivity(i);
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
