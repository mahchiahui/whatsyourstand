package com.app.dao;

import com.app.utility.ConnectionManager;
import com.app.utility.DateUtil;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO functions processing table "token" in Q&A system's db
 * as part of data persistence layer.
 */
public class TokenDAO {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TokenDAO.class);

    /**
     * Create a new token in db.
     * @param token an object of Token entity class
     * @param timestamp string presentation of current time stamp
     * @return true if insert succeeds, false if it fails
     */
    public static boolean insertToken(String token, String timestamp){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int tokenID = 12;

        // obtain current max token id
        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement("select IFNULL(MAX(tokenid),0) from token");
            rs = stmt.executeQuery();
            rs.next();
            tokenID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in insertToken, obtaining max token id",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        // create new token id
        tokenID++;

        // insert token into database
        String sql = "INSERT INTO token (tokenid, token, time_stamp) VALUES (?,?,?)";

        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,tokenID);
            stmt.setString (2, token);
            stmt.setString(3, timestamp);

            int result = stmt.executeUpdate();
            if(result == 0){
                return false;
            }
        } catch (SQLException se) {
            logger.error("sql exception in insertToken, inserting token into 14819db",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }


    /**
     * Check if a token string exists in db
     * @param token a string of token
     * @return true if token exists, false if not
     */
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
                String dbToken = rs.getString(2);
                String dbTimestamp = rs.getString(3);
                String curTime = DateUtil.getCurrentTime();

                if (token.equals(dbToken) &&
                    DateUtil.isTimeDiffLessThanOneDay(curTime, dbTimestamp)) {
                    result = true;
                }

                //refer to java api for more methods
            }
        } catch (SQLException se) {
            logger.error("sql exception in checkToken, retrieving token from 14819db",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
}
