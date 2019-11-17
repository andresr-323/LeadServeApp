package com.example.leadserve;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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

public class homepage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<Student> Students = new ArrayList();
    private ArrayList<Event> Events = new ArrayList();
    private String ID;
    public Spinner spinner;
    private String tier;
    private String name;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.navSpinner);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array, R.layout.spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);


        Intent intent = getIntent();
        ID = intent.getExtras().getString("ID");
        tier = intent.getExtras().getString("tier");
        name = intent.getExtras().getString("name");

        Bundle studs = intent.getBundleExtra("STUDBUNDLE");
        Students = (ArrayList<Student>) studs.getSerializable("STUD");
        Bundle events = intent.getBundleExtra("EVENTBUNDLE");
        Events = (ArrayList<Event>) events.getSerializable("EVEN");




    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_bar, menu);
//        // get Spinner
////        MenuItem spinnerMenuItem = menu.findItem(R.id.miSpinner);
////        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(spinnerMenuItem);
////        spinner.setOnItemSelectedListener(this);
////
////        // set Spinner Adapter
////        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array, android.R.layout.simple_spinner_dropdown_item);
////        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinner.setAdapter(spinnerAdapter);
//        return true;
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Intent i;
        Bundle args = new Bundle();
        switch (item) {
            case "Navigation":
                //do nothing
                break;
            case "Events":
                i = new Intent(homepage.this, EventsActivity.class);
                args.putSerializable("EVENT", Events);
                i.putExtra("EVENTBUNDLE",args);
                startActivity(i);
                spinner.setSelection(0);
                break;
            case "Progress":
                i = new Intent(homepage.this, ProgressActivity.class);
                i.putExtra("ID", ID);
                i.putExtra("tier", tier);
                startActivity(i);
                spinner.setSelection(0);
                 break;
            case "My Information":
                i = new Intent(homepage.this, MyInfoActivity.class);
                args.putSerializable("STUD", Students);
                i.putExtra("STUDBUNDLE",args);
                i.putExtra("ID", ID);
                startActivity(i);
                spinner.setSelection(0);
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
