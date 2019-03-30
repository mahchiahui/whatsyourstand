package com.app.entity;

public class User {
    private int userId;
    private String username;
    private String hashpwd;
    private String salt;

    private int role;
    private int requestDel;

    public User(int userId, String username, String pwd, String salt) {
        this.userId = userId;
        this.username = username;
        this.hashpwd = pwd;
        this.salt = salt;
        this.role = 0;
        this.requestDel = 0;
    }

    public User(int userId, String username, String pwd, String salt, int role, int req_del) {
        this.userId = userId;
        this.username = username;
        this.hashpwd = pwd;
        this.salt = salt;
        this.role = role;
        this.requestDel = req_del;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashpwd() {
        return hashpwd;
    }

    public void setHashpwd(String hashpwd) {
        this.hashpwd = hashpwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRequestDel() {
        return requestDel;
    }

    public void setRequestDel(int requestDel) {
        this.requestDel = requestDel;
    }
}
