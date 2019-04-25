package com.app.entity;

public class ReportedQuestion {
    private int questionID;
    private String title;
    private String description;
    private String ReportedIssue;

    public ReportedQuestion(int questionID, String title, String description, String reportedIssue) {
        this.questionID = questionID;
        this.title = title;
        this.description = description;
        ReportedIssue = reportedIssue;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
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

    public String getReportedIssue() {
        return ReportedIssue;
    }

    public void setReportedIssue(String reportedIssue) {
        ReportedIssue = reportedIssue;
    }
}
