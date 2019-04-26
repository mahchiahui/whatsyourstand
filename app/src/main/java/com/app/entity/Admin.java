package com.app.entity;

/**
 * A mapping of table "admin" in Q&A system's db.
 * This entity class belongs to data layer, served as part of data persistence layer.
 */
public class Admin {

    private int userId;
    private int level;

    public Admin (int userId, int level) {
        this.userId = userId;
        this.level = level;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
