package com.app.entity;

public class Candidate extends User {

    private String name;
    private int age;
    private String location;
    private String workplace;
    private String politicalAffiliation;
    private String politicalGoal;
    private String education;
//    private int totalUpvote;
//    private int totalDownvote;
    private String profilePhotoPath;


    public Candidate (int userId, String username, String pwd, String name,
                     int age, String location, String workplace, String politicalAffiliation, String politicalGoal, String education) {
        super(userId, username, pwd);
        this.setRole(2);
        this.name = name;
        this.age = age;
        this.location = location;
        this.workplace = workplace;
        this.politicalAffiliation = politicalAffiliation;
        this.politicalGoal = politicalGoal;
        this.education = education;
//        this.totalUpvote = 0;
//        this.totalDownvote = 0;
        this.profilePhotoPath = "/";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public int getTotalUpvote() {
//        return totalUpvote;
//    }

//    public void setTotalUpvote(int totalUpvote) {
//        this.totalUpvote = totalUpvote;
//    }

//    public int getTotalDownvote() {
//        return totalDownvote;
//    }

//    public void setTotalDownvote(int totalDownvote) {
//        this.totalDownvote = totalDownvote;
//    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
}
