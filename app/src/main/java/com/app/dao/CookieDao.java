package com.app.dao;

import com.app.entity.Cookie;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * DAO functions processing table "cookie" in Q&A system's db
 * as part of data persistence layer.
 */
public class CookieDao {

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement stmt = null;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CookieDao.class);

    /**
     * Insert a new record of cookie into db.
     * @param cookieId
     * @param userId
     * @param timestamp
     */
    public static void insertCookie (String cookieId, String userId, String timestamp) {

        try {
            conn = ConnectionManager.getConnection("14819db");
            String sqlInsert = "INSERT INTO cookie (cookieid, userid, time_stamp) VALUES ('"
                + cookieId + "', '" + userId + "', '" + timestamp + "')";
            logger.info("sqlInsert: " + sqlInsert);
            stmt = conn.prepareStatement(sqlInsert);
            stmt.executeUpdate();

        }
        catch (SQLException se) {
            logger.error("sql exception in insert cookie",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }

    }


    /**
     * Search for cookie by cookie id.
     * @param cookieId
     * @return an object of Cookie entity class
     */
    public static Cookie searchCookie (String cookieId) {
        Cookie cookie = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conn = ConnectionManager.getConnection("14819db");
            String sql = "SELECT * FROM cookie WHERE cookieid='" + cookieId + "'";
            logger.info("sqlSearch: " + sql);
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String cookieid = rs.getString(1);
                String userid = rs.getString(2);
                String timestamp = df.format(rs.getTimestamp("time_stamp"));

                if (cookieId.equals(cookieid)) {
                    cookie = new Cookie(cookieid, userid, timestamp);
                    break;
                }
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in search cookie",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return cookie;
    }
}
