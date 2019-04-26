package com.app.entity;

/**
 * A mapping of table "status" in Q&A system's db.
 * This entity class belongs to data layer, served as part of data persistence layer.
 */
public class Status {

    private int questionId;
    private int userId;
    private int statusType;  // 1: upvote, 2: downvote. 0: none of the status is marked

    public Status() {
        this.questionId = 0;
        this.userId = 0;
        this.statusType = 0;
    }

    public Status(int questionId, int userId, int type) {
        this.questionId = questionId;
        this.userId = userId;
        this.statusType = type;
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

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

}
