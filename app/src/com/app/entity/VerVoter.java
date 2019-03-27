package com.app.entity;

public class VerVoter {
    private int voterID;
    private String hashedPhoneNumber;
    private String city;
    private String locationDocument;
    private String email;
    private String name;

    public VerVoter(int voterID, String hashedPhoneNumber, String city, String locationDocument, String email, String name) {
        this.voterID = voterID;
        this.hashedPhoneNumber = hashedPhoneNumber;
        this.city = city;
        this.locationDocument = locationDocument;
        this.email = email;
        this.name = name;
    }

    public int getVoterID() {
        return voterID;
    }

    public void setVoterID(int voterID) {
        this.voterID = voterID;
    }

    public String getHashedPhoneNumber() {
        return hashedPhoneNumber;
    }

    public void setHashedPhoneNumber(String hashedPhoneNumber) {
        this.hashedPhoneNumber = hashedPhoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocationDocument() {
        return locationDocument;
    }

    public void setLocationDocument(String locationDocument) {
        this.locationDocument = locationDocument;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
