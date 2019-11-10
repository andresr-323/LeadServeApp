package com.example.leadserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class tierAdapter extends ArrayAdapter<tierOne> {
    private ArrayList<tierOne> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView step1;
        TextView step2;
        TextView step3;
    }

    public tierAdapter(ArrayList<tierOne> data, Context context) {
        super(context, R.layout.list_tier, data);
        this.dataSet = data;
        this.mContext=context;
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        tierOne dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_tier, parent, false);
            viewHolder.step1 = (TextView) convertView.findViewById(R.id.step1complete);
            viewHolder.step2 = (TextView) convertView.findViewById(R.id.step2complete);
            viewHolder.step3 = (TextView) convertView.findViewById(R.id.step3complete);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;
        String s1 = String.valueOf(dataModel.getCoachingProgram());
        String s2 = String.valueOf(dataModel.getLEAD1000());
        String s3 = String.valueOf(dataModel.getShowcase());
        viewHolder.step1.setText(s1);
        viewHolder.step2.setText(s2);
        viewHolder.step3.setText(s3);
        // Return the completed view to render on screen
        return convertView;
    }
}
