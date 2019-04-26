package com.app.entity;

/**
 * A mapping of table "answer" in Q&A system's db.
 * This entity class belongs to data layer, served as part of data persistence layer.
 */
public class Answer {

    private int answerId;
    private int questionId;
    private int userId;
    private String content;
    private String createdTime;
    private String lastModifiedTime;

    private int upvote;
    private int downvote;

    public Answer () {
        this.answerId = 0;
        this.questionId = 0;
        this.userId = 0;
        this.content = "";
        this.createdTime = "";
        this.lastModifiedTime = "";
        this.upvote = 0;
        this.downvote = 0;
    }

    public Answer (int answerId, int questionId, int userId, String content, String time) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.userId = userId;
        this.content = content;
        this.createdTime = time;
        this.lastModifiedTime = time;
        this.upvote = 0;
        this.downvote = 0;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
