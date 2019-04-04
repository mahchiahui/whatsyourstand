package com.app.entity;

public class Voter extends User {

//    private String hashedPhoneNumber;
    private String location;
    private String email;
//    private String docPath;

    public Voter(int userId, String username, String pwd, String location, String email) {
        super(userId, username, pwd);
        this.setRole(1);
//        this.hashedPhoneNumber = hashedPhoneNumber;
        this.location = location;
        this.email = email;
//        this.docPath = "/";
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
