package com.app.dao;

import com.app.entity.VerVoter;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VerVoterDAO {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VerVoterDAO.class);

    /**
     * set Verification voter in verification database
     * @param hashedPN
     * @param city
     * @param locationDocumentPath
     * @param email
     * @param name
     * @return result of sql operation
     */
    public static boolean setVerVoter(String hashedPN, String city, String locationDocumentPath, String email, String name){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int voterID = 0;

        //count number of voters
        try {
            conn = ConnectionManager.getConnection("verification");

            stmt = conn.prepareStatement("select IFNULL(MAX(voterID),0) from voter");
            rs = stmt.executeQuery();
            rs.next();
            voterID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in setVerVoter, counting voter sql",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        //create new voterID
        voterID++;

        //insert voter into database
        String sql = "INSERT INTO voter (voterID, name, hashedPN, city, locationDocumentPath, email) VALUES (?,?,?,?,?,?)";

        try {
            conn = ConnectionManager.getConnection("verification");

            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, voterID);
            stmt.setString(2, name);
            stmt.setString(3, hashedPN);
            stmt.setString(4, city);
            stmt.setString(5, locationDocumentPath);
            stmt.setString(6, email);

            int result = stmt.executeUpdate();
            if(result == 0){
                return false;
            }
        } catch (SQLException se) {
            logger.error("sql exception in setVerVoter, inserting voter in to db",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }

    public static ArrayList<VerVoter> getAllVerVoter(){
        ArrayList<VerVoter> allVerVoter = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("verification");

            stmt = conn.prepareStatement("select * from voter");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int voterID = rs.getInt(1);
                String name = rs.getString(2);
                String hashedPN = rs.getString(3);
                String city = rs.getString(4);
                String documentPath = rs.getString(5);
                String email = rs.getString(6);
                VerVoter tmpVoter = new VerVoter(voterID,hashedPN,city,documentPath,email,name);
                allVerVoter.add(tmpVoter);
            }
        } catch (SQLException se) {
            logger.error("sql exception in getAllVerVoter, counting voter sql",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return allVerVoter;
    }

    public static void deleteVerVoter(ArrayList<String> list){
        for(String voterID: list){
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement stmt = null;
            String sql = "DELETE from voter where voterID = ?";

            try {
                conn = ConnectionManager.getConnection("verification");

                stmt = conn.prepareStatement(sql);
                stmt.setInt (1, Integer.parseInt(voterID));

                stmt.executeUpdate();
            } catch (SQLException se) {
                logger.error("sql exception in setVerVoter, inserting voter in to db",se);

            } finally {
                ConnectionManager.close(conn, stmt, rs);
            }
        }

    }
}
