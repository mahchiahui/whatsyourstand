package com.app.dao;

import com.app.utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDAO {
    public static boolean insertToken(String token, String timestamp){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        //insert voter into database
        String sql = "INSERT INTO token (token, timestamp) VALUES (?,?)";

        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement(sql);
            stmt.setString (1, token);
            stmt.setString(2, timestamp);

            int result = stmt.executeUpdate();
            if(result == 0){
                return false;
            }
        } catch (SQLException se) {
            Logger.getLogger("TokenDAO").log(Level.SEVERE, "broke in insertToken, inserting token into 14819db", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }

    public static boolean checkToken(String token){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement("select * from token");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String dbToken = rs.getString(1);
                String dbTimestamp = rs.getString(2);
                if (token.equals(dbToken)) {
                    result = true;
                }

                //refer to java api for more methods
            }
        } catch (SQLException se) {
            Logger.getLogger("TokenDAO").log(Level.SEVERE, "broke in checkToken, retrieving token from 14819db", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
}
