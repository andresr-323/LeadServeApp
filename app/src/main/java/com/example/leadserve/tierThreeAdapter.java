package com.example.leadserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class tierThreeAdapter extends ArrayAdapter<tierThree> {
    private ArrayList<tierThree> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView step1;
        TextView step2;
        TextView step3;
        TextView step4;
    }

    public tierThreeAdapter(ArrayList<tierThree> data, Context context) {
        super(context, R.layout.list_tier3, data);
        this.dataSet = data;
        this.mContext=context;
    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        tierThree dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_tier2, parent, false);
            viewHolder.step1 = (TextView) convertView.findViewById(R.id.step1complete);
            viewHolder.step2 = (TextView) convertView.findViewById(R.id.step2complete);
            viewHolder.step3 = (TextView) convertView.findViewById(R.id.step3complete);
            viewHolder.step4 = (TextView) convertView.findViewById(R.id.step4complete);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;
        String s1 = String.valueOf(dataModel.getLeadershipLegProj());
        String s2 = String.valueOf(dataModel.getLEAD3000());
        String s3 = String.valueOf(dataModel.getLeadershipPort());
        String s4 = String.valueOf(dataModel.getShowcase());
        viewHolder.step1.setText(s1);
        viewHolder.step2.setText(s2);
        viewHolder.step3.setText(s3);
        viewHolder.step4.setText(s4);
        // Return the completed view to render on screen
        return convertView;
    }
}