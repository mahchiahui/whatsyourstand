package com.app.entity;

public class Token {
    private int tokenId;
    private String token;
    private String timestamp;
    private static final int EXPIRATION_HOURS = 24;

    public Token (int id, String token, String timestamp) {
        this.tokenId = id;
        this.token = token;
        this.timestamp = timestamp;
    }

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
