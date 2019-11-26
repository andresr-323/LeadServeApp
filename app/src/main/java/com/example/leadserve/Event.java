package com.example.leadserve;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Event implements Serializable {
    private int eventID;
    private String title;
    private String description;
    private String location;
    private String campus;
    private String date;
    private Date de;
    private String time;
    private int docID;
    private String imgPath;
    private String t1;
    private String t2;
    private String t3;


    private final String URL = "http://52.45.183.203/eventImages/";

    Event(){

    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        de = d;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String result = df.format(d);
        this.date = result;
    }

    public Date getNonStringDate(){
        return de;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = URL + imgPath;
    }

    public void setTiers(String s){
        String[] res;
        res = s.split(", ");
        if(res[0].equals("")){
            this.t1 = "0";
            this.t2 = "0";
            this.t3 = "0";
        }else {
            int cnt = 0;
            for (int i = 0; i < res.length; i++, cnt++) {
                if (cnt == 0)
                    this.t1 = res[0];
                if (cnt == 1)
                    this.t2 = res[1];
                if (cnt == 2)
                    this.t3 = res[2];
            }
        }
    }

    public String[] getTiers(){
        String[] s = new String[3];
        if(t1 != null){
            s[0] = t1;
            if(t2 != null){
                s[1] = t2;
                if(t3 !=null){
                    s[2] = t3;
                }else{
                    s[2] = "0";
                }
            }else{
                s[1] = "0";
                s[2] = "0";
            }
        }else{
            s[0] = "0";
            s[1] = "0";
            s[2] = "0";
        }

        return s;
    }
}
