package com.app.dao;

import com.app.entity.Report;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO functions processing table "report" in Q&A system's db
 * as part of data persistence layer.
 */
public class ReportDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionDAO.class);

    /**
     * Check if a report from a user exists in the db.
     * @param report an object of report entity class
     * @return true if delete succeeds, false if it fails
     */
    public static boolean checkReportExist (Report report) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean exist = false;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM report WHERE " +
                "questionid=? AND userid=?");
            stmt.setInt(1, report.getQuestionId());
            stmt.setInt(2, report.getUserId());
            rs = stmt.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in checkReportExist", se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return exist;
    }


    /**
     * Insert a new record of question report
     * @param report an object of report entity class
     */
    public static void createReport (Report report) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int reportID = 0;

        //count number of report
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("select IFNULL(MAX(reportid),0) from report");
            rs = stmt.executeQuery();
            rs.next();
            reportID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in createReport, counting report sql",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        //create new reportID
        reportID++;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "INSERT INTO report (reportid, questionid, userid, content) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, reportID);
            stmt.setInt (2, report.getQuestionId());
            stmt.setInt (3, report.getUserId());
            stmt.setString(4, report.getContent());

            stmt.executeUpdate();  // int result =

        } catch (SQLException se) {
            logger.error("sql exception in createStatus", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }


    /**
     * Remove a record of report from db.
     * @param report an object of report entity class
     * @return true if delete succeeds, false if it fails
     */
    public static boolean deleteReport (Report report) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM report WHERE questionid=? AND userid=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, report.getQuestionId());
            stmt.setInt (2, report.getUserId());

            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteReport", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return false;
    }
}
