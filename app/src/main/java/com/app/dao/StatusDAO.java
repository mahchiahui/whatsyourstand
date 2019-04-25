package com.app.dao;

import com.app.entity.Status;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class manipulates the crud of Status table for: Like, Upvote, Downvote
 */
public class StatusDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionDAO.class);

    /**
     * Check if there is record of status for a user on certain question
     * @param status
     * @return
     */
    public static boolean checkStatus (Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean exist = false;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM status WHERE " +
                "questionid=? AND userid=? AND status_type=?");
            stmt.setInt(1, status.getQuestionId());
            stmt.setInt(2, status.getUserId());
            stmt.setInt(3, status.getStatusType());
            rs = stmt.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in checkStatus", se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return exist;
    }


    /**
     * Insert a new record of status
     * @param status
     */
    public static void createStatus (Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "INSERT INTO status (questionid, userid, status_type) VALUES (?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, status.getQuestionId());
            stmt.setInt (2, status.getUserId());
            stmt.setInt (3, status.getStatusType());

            stmt.executeUpdate();  // int result =

        } catch (SQLException se) {
            logger.error("sql exception in createStatus", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }


    /**
     * Remove a record of status from db
     * @param status
     */
    public static boolean deleteStatus (Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM status WHERE questionid=? AND userid=? AND status_type=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, status.getQuestionId());
            stmt.setInt (2, status.getUserId());
            stmt.setInt (3, status.getStatusType());

            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteStatus", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return false;
    }
}
