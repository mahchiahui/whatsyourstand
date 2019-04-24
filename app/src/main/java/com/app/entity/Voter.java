package com.app.entity;

public class Voter {

    private int userId;



    private String username;
    private String location;
    private String email;

    public Voter(int userId, String username, String location, String email) {
        this.userId = userId;
        this.location = location;
        this.email = email;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
