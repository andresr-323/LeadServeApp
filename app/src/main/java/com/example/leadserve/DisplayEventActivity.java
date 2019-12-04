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
import java.util.ArrayList;

public class DisplayEventActivity extends AppCompatActivity {
    Event sel;
    TextView title;
    TextView date;
    TextView time;
    TextView location;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        //get the selected event object to display its data
        sel = (Event)getIntent().getSerializableExtra("event");
        title = findViewById(R.id.displayTitle);
        date = findViewById(R.id.displayDate);
        time = findViewById(R.id.displayTime);
        location = findViewById(R.id.displayLocation);
        desc = findViewById(R.id.displayDescription);

        Toolbar tb = findViewById(R.id.toolbar);
        tb.setTitle(sel.getTitle());
        tb.setTitleTextColor(getResources().getColor(R.color.SLgold));

        new DisplayEventActivity.DownloadImageTask((ImageView) findViewById(R.id.displayEventImg)).execute(sel.getImgPath());
        title.setText("Event: " + sel.getTitle());
        date.setText("Date: " + sel.getDate());
        time.setText("Start time: " + sel.getTime());
        location.setText(sel.getCampus() + ", " +sel.getLocation());
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
