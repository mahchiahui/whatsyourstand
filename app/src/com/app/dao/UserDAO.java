package com.app.dao;

import com.app.entity.User;
import com.app.utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    public static User retrieveUser(){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        User user = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement("select * from user");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                String username = rs.getString(2);
                user = new User(userID, username);
                //refer to java api for more methods
            }
        } catch (SQLException se) {
            Logger.getLogger("UserDAO").log(Level.SEVERE, null, se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }

    public static boolean insertUser(String password){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int userID = 0;

        //count number of voters
        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement("select Count(Distinct userid) from user");
            rs = stmt.executeQuery();
            rs.next();
            userID = rs.getInt(1);
        } catch (SQLException se) {
            Logger.getLogger("UserDAO").log(Level.SEVERE, "broke in insertUser, counting user sql", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        //create new voterID
        userID++;

        //insert user into database
        String sql = "INSERT INTO user (userid, password) VALUES (?,?)";

        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, userID);
            stmt.setString(2, password);

            int result = stmt.executeUpdate();
            if(result == 0){
                return false;
            }
        } catch (SQLException se) {
            Logger.getLogger("UserDAO").log(Level.SEVERE, "broke in insertUser, inserting user in", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }
}