package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private String ID;
    private ArrayList<Student> Students;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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
        Button bt = findViewById(R.id.logoutBtn);
        bt.setOnClickListener(this);

        TextView Name = findViewById(R.id.name);
        TextView tier = findViewById(R.id.tier);
        TextView advisor = findViewById(R.id.advisorInp);
        TextView campus = findViewById(R.id.campusInp);
        TextView major = findViewById(R.id.majorInp);
        TextView grad = findViewById(R.id.gDateInp);
        TextView town = findViewById(R.id.townInp);

        Name.setText(s.getName());
        tier.setText("Tier Number: " + s.getTierNumber());
        advisor.setText(s.getAdvisor());
        campus.setText(s.getCampus());
        major.setText(s.getMajor());
        grad.setText(s.getExpectedGrad());
        town.setText(s.getHometown());

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
