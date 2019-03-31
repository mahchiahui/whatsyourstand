package com.app.entity;

public class Cookie {

    private String cookieId;
    private String userId;
    private String timestamp;

    public Cookie (String cookieId, String userId, String timestamp) {
        this.cookieId = cookieId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
