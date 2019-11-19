package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayEventActivity extends AppCompatActivity {
    Event sel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        //get the selected event object to display its data
        sel = (Event)getIntent().getSerializableExtra("event");
        Toast.makeText(this, "event name" + sel.getTitle(),Toast.LENGTH_SHORT).show();
    }
}
