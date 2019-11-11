package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity {
    private String ID;
    private ArrayList<Student> Students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Information");
        toolbar.setTitleTextColor(getResources().getColor(R.color.SLgold));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        ID = intent.getExtras().getString("ID");

        Bundle studs = intent.getBundleExtra("STUDBUNDLE");
        Students = (ArrayList<Student>) studs.getSerializable("STUD");
        Student s = new Student();
        for(Student s1: Students){
            String i = String.valueOf(s1.getID());
            if(i.equals(ID)){
                s = s1;
            }
        }

        TextView Name = findViewById(R.id.Name);
        TextView Email = findViewById(R.id.Email);
        TextView Major = findViewById(R.id.major);
        TextView Advisor  = findViewById(R.id.advisor);

        Name.setText("Name: " + s.getName());
        Email.setText("Email: " + s.getEmail());
        Major.setText("Major: " + s.getMajor());
        Advisor.setText("Advisor: " + s.getAdvisor());

    }



}
