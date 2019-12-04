package com.example.leadserve;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity{
    TextView UsernameEt, PasswordEt;
    Button logBtn;
    private FirebaseAuth mAuth;
    private static Context context;
    private AlertDialog alertDialog;

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
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        username = "andresrodriguez1337@gmail.com";
        password = "password";


        String type = "login";
        if(username.equals("") && password.equals("")){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status:");
            alertDialog.setMessage("Enter both email and password please.");
            alertDialog.show();
        }else if(username.equals("")){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status:");
            alertDialog.setMessage("Email cannot be blank.");
            alertDialog.show();
        }else if(password.equals("")){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status:");
            alertDialog.setMessage("Password cannot be blank.");
            alertDialog.show();
        }else if(!isValid(username)){
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Status:");
            alertDialog.setMessage("Email is not valid.");
            alertDialog.show();
        }else{
            loginCheck backgroundWorker = new loginCheck(this, username, password);
            backgroundWorker.execute(type, username, password);
        }
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void Fin(){
        finish();
    }

}
