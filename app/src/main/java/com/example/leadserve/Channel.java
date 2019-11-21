package com.example.leadserve;

public class Channel {

    private String id;
    private String name;
    private String from;

    public Channel(String name, String from) {
        this.id = null;
        this.name = name;
        this.from = from;
    }
    public Channel(String tier){
        this.id = tier;
        this.name = tier;
        this.from = "";
    }
    public Channel(String id, String name, String from){
        this.id = id;
        this.name = name;
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}