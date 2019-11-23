package com.example.leadserve;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {
    String ID;
    private ArrayList<Student> Students = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Messaging");
        toolbar.setTitleTextColor(getResources().getColor(R.color.SLgold));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        ID = intent.getExtras().getString("ID");

        Bundle studs = intent.getBundleExtra("STUDBUNDLE");
        Students = (ArrayList<Student>) studs.getSerializable("STUD");

        String[] res = new String[Students.size()];
        int i = 0;
        for(Student s: Students){
            res[i] = s.getName();
            i++;
        }

//        ListView lv = findViewById(R.id.listviewMessaging);
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        lv.setAdapter(itemsAdapter);

    }

}
