package com.app.entity;

public class Question {

    private int questionId;
    private int userId;
    private String title;
    private String description;
    private String createdTime;
    private String lastModifiedTime;
    private String location;

    private int numberOfAnswer;
    private int upvote;
    private int downvote;
    private int likes;

    private int flagProblematic;

    public Question () {
        this.questionId = 0;
        this.userId = 0;
        this.title = "";
        this.description = "";
        this.createdTime = "";
        this.lastModifiedTime = "";
        this.location = "";

        this.numberOfAnswer = 0;
        this.upvote = 0;
        this.downvote = 0;
        this.likes = 0;
        this.flagProblematic = 0;
    }

    public Question (int qid, int uid, String title, String description, String time, String location) {
        this.questionId = qid;
        this.userId = uid;
        this.title = title;
        this.description = description;
        this.createdTime = time;
        this.lastModifiedTime = "";
        this.location = location;

        this.numberOfAnswer = 0;
        this.upvote = 0;
        this.downvote = 0;
        this.likes = 0;
        this.flagProblematic = 0;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfAnswer() {
        return numberOfAnswer;
    }

    public void setNumberOfAnswer(int numberOfAnswer) {
        this.numberOfAnswer = numberOfAnswer;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int isFlagProblematic() {
        return flagProblematic;
    }

    public void setFlagProblematic(int flagProblematic) {
        this.flagProblematic = flagProblematic;
    }
}
