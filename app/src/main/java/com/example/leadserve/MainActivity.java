package com.example.leadserve;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements asyncCallBack{
    TextView UsernameEt, PasswordEt;
    Button logBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = findViewById(R.id.emailText);
        PasswordEt = findViewById(R.id.passText);
        logBtn = findViewById(R.id.loginBtn);
    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        loginCheck backgroundWorker = new loginCheck(this);
        backgroundWorker.execute(type, username, password);
    }

    public void done() {
        this.finish();
    }

}
