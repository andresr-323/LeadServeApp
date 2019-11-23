package com.example.leadserve;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String content;
    private String senderID;
    private String senderName;
    private String created;
    private String toId;

    public Message(String content, String senderID, String senderName, String toId) {
        this.content = content;
        this.senderID = senderID;
        this.senderName = senderName;
        this.toId = toId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");
        String currentDateandTime = sdf.format(new Date());
        this.created = currentDateandTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderID(){
        return senderID;
    }

    public void setSenderID(String senderID){
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getcreated() {
        return created;
    }

    public void setcreated(String date) {
        this.created = date;
    }
}