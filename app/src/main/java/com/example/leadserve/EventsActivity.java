package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    String ID;
    private ArrayList<Event> Events = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        Bundle studs = intent.getBundleExtra("EVENTBUNDLE");
        Events = (ArrayList<Event>) studs.getSerializable("EVENT");
    }
}
