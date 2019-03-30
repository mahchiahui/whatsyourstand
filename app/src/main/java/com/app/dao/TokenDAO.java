package com.app.dao;

import com.app.entity.Token;
import com.app.utility.ConnectionManager;
import com.app.utility.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDAO {

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement stmt = null;

    public TokenDAO () {
    }


    /**
     * See if a token exists in the db, and if exists whether is hasn't expired
     * @param token
     * @return
     */
    public boolean isTokenValid (String token) {
        // search for this token
        Token tok = searchToken(token);
        String curTime = DateUtil.getCurrentTime();

        return (tok != null && DateUtil.isTimeDiffLessThanOneDay(curTime, tok.getTimestamp()));
    }


    /**
     * Insert a token into db using current timestamp,
     * if token doesn't exist in the db
     * @param token : pre-generated token
     * @return
     */
    public boolean insertToken (String token) {
        // if token doesn't exist, insert it into db
        Token tok = searchToken(token);
        if (tok == null) {
            // get current timestamp
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String timestamp = dtf.format(now);

            return insertToken(token, timestamp);
        }
        else {
            System.out.println("This token already exists in db.");
            return false;
        }
    }


    /**
     * Insert a token into db using a given timestamp
     * @param token
     * @param timestamp
     * @return
     */
    public boolean insertToken (String token, String timestamp) {
        // INSERT INTO token (tokenid, token, timestamp) VALUES ('1', 'token1', '2019-03-29 10:15:17');
        int curMaxId = 0;
        String tokenId = null;
        boolean flagSuccess = false;

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(tokenid) AS rowcount FROM user");
            rs = stmt.executeQuery();
            rs.next();
            curMaxId = rs.getInt("rowcount");
//            System.out.println("curMaxId: " + curMaxId);
            tokenId = Integer.toString(curMaxId + 1);

            String sqlInsert = "INSERT INTO user (tokenid, token, timestamp) VALUES ("
                +  tokenId + ", '" + token + "', '" + timestamp + "')";
            System.out.println("sqlInsert: " + sqlInsert);
            stmt = conn.prepareStatement(sqlInsert);
//            rs = stmt.executeQuery();
            stmt.executeUpdate();
            flagSuccess = true;
        }
        catch (SQLException se) {
//            Logger.getLogger("UserDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("UserDAO").log(Level.SEVERE, se.getMessage(), se);  // use java.util.Logger for this
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return flagSuccess;
    }


    /**
     * Search for a given token string
     * @param token : given token string
     * @return : null if token doesn't exist
     */
    public Token searchToken (String token) {
        Token tok = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM token WHERE token=\""
                + token + "\"");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int tokenid = rs.getInt(1);
                String strToken = rs.getString("token");
                String timestamp = df.format(rs.getTimestamp("timestamp"));
                if (strToken.equals(token)) {
                    tok = new Token(tokenid, strToken, timestamp);
                    break;
                }
            }
        }
        catch (SQLException se) {
//            Logger.getLogger("UserDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("TokenDAO").log(Level.SEVERE, null, se);  // use java.util.Logger for this
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return tok;
    }


    /**
     * Delete all tokens out of the time limit 24 hours, compared to current timestamp
     * @param curTimeStamp
     */
    public void deleteExpiredToken (String curTimeStamp) {
        // TODO
    }
}
