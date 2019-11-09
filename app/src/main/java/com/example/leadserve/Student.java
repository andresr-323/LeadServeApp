package com.example.leadserve;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private int ID;
    private String email;
    private String tierNumber;
    private String advisor;
    private String campus;
    private String major;
    private String prefName;
    private String expectedGrad;
    private String hometown;
    private String tshirtSize;
    private int archived;

    Student(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTierNumber() {
        return tierNumber;
    }

    public void setTierNumber(String tierNumber) {
        this.tierNumber = tierNumber;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPrefName() {
        return prefName;
    }

    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }

    public String getExpectedGrad() {
        return expectedGrad;
    }

    public void setExpectedGrad(String expectedGrad) {
        this.expectedGrad = expectedGrad;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getTshirtSize() {
        return tshirtSize;
    }

    public void setTshirtSize(String tshirtSize) {
        this.tshirtSize = tshirtSize;
    }

    public int getArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
    }

}
