package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

public class EventsActivity extends AppCompatActivity {
    String ID;
    String tier;
    private ArrayList<Event> Events = new ArrayList();
    private ArrayList<Event> relEvents = new ArrayList<>();
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

        ListView lv = findViewById(R.id.EventList);
        EventAdapter ed = new EventAdapter(Events, this);
        lv.setAdapter(ed);

    }

}
