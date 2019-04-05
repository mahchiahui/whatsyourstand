package com.app.entity;

public class Rootuser {

    private int userId;
    private String username;
    private String hashpwd;

    private int role;  // 0: admin; 1: voter; 2: candidate
    private int requestDel;

    public Rootuser(int userId, String username, String pwd) {
        this.userId = userId;
        this.username = username;
        this.hashpwd = pwd;

        this.role = 0;
        this.requestDel = 0;
    }

    public Rootuser(int userId, String username, String pwd, int role, int req_del) {
        this.userId = userId;
        this.username = username;
        this.hashpwd = pwd;

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
