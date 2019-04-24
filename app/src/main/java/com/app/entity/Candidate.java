package com.app.entity;

public class Candidate {

    private int userId;
    private String realname;
    private int age;
    private String location;
    private String workplace;
    private String politicalAffiliation;
    private String politicalGoal;
    private String education;
    private String profilePhotoPath;

    public Candidate () {
        this.userId = 0;
        this.realname = "";
        this.age = 0;
        this.location = "";
        this.workplace = "";
        this.politicalAffiliation = "";
        this.politicalGoal = "";
        this.education = "";
        this.profilePhotoPath = "/";
    }

    public Candidate (int userId, String realname, int age, String location,
                      String workplace, String politicalAffiliation, String politicalGoal, String education) {

        this.userId = userId;
        this.realname = realname;
        this.age = age;
        this.location = location;
        this.workplace = workplace;
        this.politicalAffiliation = politicalAffiliation;
        this.politicalGoal = politicalGoal;
        this.education = education;
        this.profilePhotoPath = "/" + String.valueOf(userId);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getPoliticalAffiliation() {
        return politicalAffiliation;
    }

    public void setPoliticalAffiliation(String politicalAffiliation) {
        this.politicalAffiliation = politicalAffiliation;
    }

    public String getPoliticalGoal() {
        return politicalGoal;
    }

    public void setPoliticalGoal(String politicalGoal) {
        this.politicalGoal = politicalGoal;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
}
