package com.example.leadserve;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class TierExpandableListAdapter  extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    static int cnt;

    public TierExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tier_list_item, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.status);
        expandedListTextView.setText(expandedListText); //setting the status
        if(expandedListText.equals("Complete")){
            expandedListTextView.setTextColor(context.getResources().getColor(R.color.complete));
        } else if(expandedListText.equals("Under Review")){
            expandedListTextView.setTextColor(context.getResources().getColor(R.color.caution));
        } else{
            expandedListTextView.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        TextView expandedListTitle = convertView.findViewById(R.id.tierChildTitle);  //to set the leftside title
        if(listPosition == 0){  //if it's tier one's sublist
            if(cnt == 0) {
                expandedListTitle.setText(R.string.L1);
                cnt++;
            }else if(cnt == 1){
                expandedListTitle.setText(R.string.coaching);
                cnt++;
            }else if(cnt == 2){
                expandedListTitle.setText(R.string.sCase);
                cnt = 0;
            }
        }else if(listPosition == 1){    //if it's tier two's sublist
            if(cnt == 0) {
                expandedListTitle.setText(R.string.L2);
                cnt++;
            }else if(cnt == 1){
                expandedListTitle.setText(R.string.fiveHour);
                cnt++;
            }else if(cnt == 2){
                expandedListTitle.setText(R.string.Lprop);
                cnt++;
            }else if(cnt == 3){
                expandedListTitle.setText(R.string.sCase);
                cnt = 0;
            }
        }
        else if(listPosition == 2){ //if it's tier three's sublist
            if(cnt == 0) {
                expandedListTitle.setText(R.string.L3);
                cnt++;
            }else if(cnt == 1){
                expandedListTitle.setText(R.string.lProjectComp);
                cnt++;
            }else if(cnt == 2){
                expandedListTitle.setText(R.string.lPort);
                cnt++;
            }else if(cnt == 3){
                expandedListTitle.setText(R.string.sCase);
                cnt = 0;
            }
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tier_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tierListTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
