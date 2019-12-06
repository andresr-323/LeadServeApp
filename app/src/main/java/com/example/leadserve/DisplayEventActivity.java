package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayEventActivity extends AppCompatActivity {
    Event sel;
    TextView title;
    TextView datetext;
    TextView time;
    TextView location;
    TextView desc;
    TextView tiers;
    TextView Campus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        //get the selected event object to display its data
        sel = (Event)getIntent().getSerializableExtra("event");
        title = findViewById(R.id.displayTitle);
        datetext = findViewById(R.id.displayDate);
        time = findViewById(R.id.displayTime);
        tiers = findViewById(R.id.tier);
        Campus = findViewById(R.id.Campus);
        location = findViewById(R.id.displayLocation);
        desc = findViewById(R.id.displayDescription);

        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle(sel.getTitle());
        tb.setTitleTextColor(getResources().getColor(R.color.SLgold));

        //Input date in String format
        String input = sel.getTime();
        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat("HH:mm");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        String output = null;
        try{
            //Conversion of input String to date
            date= df.parse(input);
            //old date format to new date format
            output = outputformat.format(date);
        }catch(ParseException pe){
            pe.printStackTrace();
        }

        new DisplayEventActivity.DownloadImageTask((ImageView) findViewById(R.id.displayEventImg)).execute(sel.getImgPath());
        title.setText(sel.getTitle());
        datetext.setText("Date: " + sel.getDate());
        time.setText("Start time: " + output);
        tiers.setText("tiers: " + sel.getStringtiers());
        Campus.setText("Campus: " + sel.getCampus());
        location.setText("Location" + sel.getLocation());
        desc.setText("Description:\n" + sel.getDescription());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result == null){

            }else {
                bmImage.setImageBitmap(result);
            }
        }
    }

}
