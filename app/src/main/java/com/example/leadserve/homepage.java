package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class homepage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ArrayList<Student> Students = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        Students= (ArrayList<Student>) b.getSerializable("Stud");
        String sa = "";
        for(Student s: Students){
            sa += s.toString();
        }
        Toast.makeText(this, sa, Toast.LENGTH_LONG).show();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_bar, menu);
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        // get Spinner
        MenuItem spinnerMenuItem = menu.findItem(R.id.miSpinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(spinnerMenuItem);
        spinner.setOnItemSelectedListener(this);

        // set Spinner Adapter
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Intent i;
        switch (item) {
            case "Navigation":
                //do nothing
                break;
            case "Homepage":
                //do nothing
                break;
            case "Events":
                i = new Intent(homepage.this, EventsActivity.class);
                startActivity(i);
                Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
                break;
            case "Progress":
                i = new Intent(homepage.this, ProgressActivity.class);
                startActivity(i);
                Toast.makeText(this, "Progress", Toast.LENGTH_SHORT).show();
                 break;
            case "Messaging":
                i = new Intent(homepage.this, MessagingActivity.class);
                startActivity(i);
                Toast.makeText(this, "Messaging", Toast.LENGTH_SHORT).show();
                break;
            case "My Information":
                i = new Intent(homepage.this, MyInfoActivity.class);
                startActivity(i);
                Toast.makeText(this, "My Information", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // action with ID action_refresh was selected
//            case R.id.action_refresh:
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }

    @Override
    public void onBackPressed() {
    }

}
