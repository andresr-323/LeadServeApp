package com.example.leadserve;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(ArrayList<Event> Events) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        ArrayList<Event> up = new ArrayList<>();
        ArrayList<Event> fut = new ArrayList<>();

        for(Event e: Events){
             //Create 2 instances of Calendar
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            //set the given date in one of the instance and current date in the other
            cal1.setTime(e.getNonStringDate());
            cal2.setTime(new Date());

            //now compare the dates using methods on Calendar
            if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) || (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))) {
                if((cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) && cal1.get(Calendar.DAY_OF_MONTH) >= cal2.get(Calendar.DAY_OF_MONTH)) {
                    // the date falls in current month and the event is in the same month or greater
                    up.add(e);
                // || (cal1.get(Calendar.YEAR) >= cal2.get(Calendar.YEAR)) might beneeded incase future next semester event?
                }else if((cal1.get(Calendar.MONTH) >= cal2.get(Calendar.MONTH)) || (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))){
                    //the date doesnt fall in the current month but is set for a later month/year
                    fut.add(e);
                }
            }


        }

        List<String> upcoming = new ArrayList<String>();
        for (Event e: up) {
            upcoming.add(e.getTitle() + "\t\t" + e.getDate());
        }


        List<String> future = new ArrayList<String>();
        for (Event e: fut) {
            future.add(e.getTitle() + "\t\t" + e.getDate());
        }
        expandableListDetail.clear();


        expandableListDetail.put("Future Events", future);
        expandableListDetail.put("This Month's Events", upcoming);

        return expandableListDetail;
    }
}
