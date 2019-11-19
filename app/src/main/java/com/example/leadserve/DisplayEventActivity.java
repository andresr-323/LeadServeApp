package com.example.leadserve;

import androidx.appcompat.app.AppCompatActivity;

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
    ImageView evImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        //get the selected event object to display its data
        sel = (Event)getIntent().getSerializableExtra("event");
        //Toast.makeText(this, "event name" + sel.getTitle(),Toast.LENGTH_SHORT).show();
        title = findViewById(R.id.displayTitle);
        date = findViewById(R.id.displayDate);
        time = findViewById(R.id.displayTime);
        location = findViewById(R.id.displayLocation);
        desc = findViewById(R.id.displayDescription);
        //evImg = findViewById(R.id.displayEventImg);

        new DisplayEventActivity.DownloadImageTask((ImageView) findViewById(R.id.displayEventImg)).execute(sel.getImgPath());
        title.setText(sel.getTitle());
        date.setText(sel.getDate());
        time.setText(sel.getTime());
        location.setText(sel.getCampus() + ": " +sel.getLocation());
        desc.setText(sel.getDescription());
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
            bmImage.setImageBitmap(result);
        }
    }

    static void convert12(String str)
    {
// Get Hours
        int h1 = (int)str.charAt(0) - '0';
        int h2 = (int)str.charAt(1)- '0';

        int hh = h1 * 10 + h2;

        // Finding out the Meridien of time
        // ie. AM or PM
        String Meridien;
        if (hh < 12) {
            Meridien = "AM";
        }
        else
            Meridien = "PM";

        hh %= 12;

        // Handle 00 and 12 case separately
        if (hh == 0) {
            System.out.print("12");

            // Printing minutes and seconds
            for (int i = 2; i < 8; ++i) {
                System.out.print(str.charAt(i));
            }
        }
        else {
            System.out.print(hh);
            // Printing minutes and seconds
            for (int i = 2; i < 8; ++i) {
                System.out.print(str.charAt(i));
            }
        }

        // After time is printed
        // cout Meridien
        System.out.println(" "+Meridien);
    }
}
