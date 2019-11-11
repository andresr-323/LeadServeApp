package com.example.leadserve;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    private ArrayList<Event> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView eTitle;
        TextView eLocation;
        TextView eTime;
        TextView eDesc;
        TextView eDate;
        ImageView eImg;
    }

    public EventAdapter(ArrayList<Event> data, Context context) {
        super(context, R.layout.list_tier, data);
        this.dataSet = data;
        this.mContext=context;
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_events, parent, false);
            viewHolder.eTitle = (TextView) convertView.findViewById(R.id.eventTitle);
            viewHolder.eLocation = (TextView) convertView.findViewById(R.id.eventLoc);
            viewHolder.eTime = (TextView) convertView.findViewById(R.id.eventTime);
            viewHolder.eDate = (TextView) convertView.findViewById(R.id.eventDate);
            viewHolder.eDesc = (TextView) convertView.findViewById(R.id.eventDesc);
            viewHolder.eImg = (ImageView) convertView.findViewById(R.id.eventIMG);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.eventIMG)).execute(dataModel.getImgPath());

        lastPosition = position;
        String s1 = String.valueOf(dataModel.getTitle());
        String s2 = String.valueOf(dataModel.getLocation());
        String s3 = String.valueOf(dataModel.getTime());
        String s4 = String.valueOf(dataModel.getDate());
        String s5 = String.valueOf(dataModel.getDescription());
        viewHolder.eTitle.setText(s1);
        viewHolder.eLocation.setText(s2);
        viewHolder.eTime.setText(s3);
        viewHolder.eDate.setText(s4);
        viewHolder.eDesc.setText(s5);

//        viewHolder.eImg.setImageURI(url);
        // Return the completed view to render on screen
        return convertView;
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
}
