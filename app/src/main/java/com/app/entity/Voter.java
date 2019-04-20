package com.app.entity;

public class Voter {

//    private String hashedPhoneNumber;
    private int userId;
    private String location;
    private String email;
//    private String docPath;

    public Voter(int userId, String location, String email) {
        this.userId = userId;
        this.location = location;
        this.email = email;
//        this.docPath = "/";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
