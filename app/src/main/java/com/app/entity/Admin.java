package com.app.entity;

public class Admin extends User {

    private int level;

    public Admin (int userId, String username, String pwd, int level) {
        super(userId, username, pwd);
        this.level = level;
    }
}
