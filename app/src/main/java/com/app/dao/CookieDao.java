package com.app.dao;

import com.app.entity.Cookie;
import com.app.utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookieDao {

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement stmt = null;

    public CookieDao() {
    }


    /**
     * Insert a new record of cookie into db
     * @param cookieId
     * @param timestamp
     */
    public static void insertCookie (String cookieId, String userId, String timestamp) {
//        boolean flagSuccess = false;

        try {
            conn = ConnectionManager.getConnection();
            String sqlInsert = "INSERT INTO cookie (cookieid, userid, timestamp) VALUES ('"
                + cookieId + "', '" + userId + "', '" + timestamp + "')";
            System.out.println("sqlInsert: " + sqlInsert);
            stmt = conn.prepareStatement(sqlInsert);
            stmt.executeUpdate();
//            flagSuccess = true;
        }
        catch (SQLException se) {
//            Logger.getLogger("CookieDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("CookieDAO").log(Level.SEVERE, se.getMessage(), se);  // use java.util.Logger for this
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
//        return flagSuccess;
    }


    /**
     * Search for cookie by cookie id
     * @param cookieId
     * @return
     */
    public static Cookie searchCookie (String cookieId) {
        Cookie cookie = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conn = ConnectionManager.getConnection();
            String sql = "SELECT * FROM cookie WHERE cookieid='" + cookieId + "'";
            System.out.println("sql: " + sql);
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String cookieid = rs.getString(1);
                String userid = rs.getString(2);
                String timestamp = df.format(rs.getTimestamp("timestamp"));

                if (cookieId.equals(cookieid)) {
                    cookie = new Cookie(cookieid, userid, timestamp);
                    break;
                }
            }
        }
        catch (SQLException se) {
//            Logger.getLogger("CookieDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("CookieDAO").log(Level.SEVERE, se.getMessage(), se);  // use java.util.Logger for this
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return cookie;
    }
}
