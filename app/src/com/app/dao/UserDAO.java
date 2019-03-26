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
            conn = ConnectionManager.getConnection();

            stmt = conn.prepareStatement("select * from user");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                user = new User(userid, username);
                //refer to java api for more methods
            }
        } catch (SQLException se) {
            Logger.getLogger("UserDAO").log(Level.SEVERE, null, se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }
}