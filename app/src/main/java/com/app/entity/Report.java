package com.app.entity;

public class Report {

    private int reportId;
    private int questionId;
    private int userId;
    private String content;

    public Report () {
        this.reportId = 0;
        this.questionId = 0;
        this.userId = 0;
        this.content = "";
    }

    public Report (int reportId, int questionId, int userId, String content) {
        this.reportId = reportId;
        this.questionId = questionId;
        this.userId = userId;
        this.content = content;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
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
}
