package com.example.leadserve;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{
    TextView UsernameEt, PasswordEt;
    Button logBtn;
    private FirebaseAuth mAuth;
    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = findViewById(R.id.emailText);
        PasswordEt = findViewById(R.id.passText);
        logBtn = findViewById(R.id.loginBtn);

    }

    public void OnLogin(View view) {
//        String username = UsernameEt.getText().toString();
//        String password = PasswordEt.getText().toString();
        String username = "andresrodriguez1337@gmail.com";
        String password = "password";
        String type = "login";
        loginCheck backgroundWorker = new loginCheck(this, username, password);
        backgroundWorker.execute(type, username, password);
    }

}
