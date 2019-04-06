package com.app.dao;

import com.app.entity.VerVoter;
import com.app.utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerVoterDAO {
    public static boolean setVerVoter(String hashedPN, String city, String locationDocumentPath, String email, String name){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int voterID = 0;

        //count number of voters
        try {
            conn = ConnectionManager.getConnection("verification");

            stmt = conn.prepareStatement("select COUNT(DISTINCT voterID) from voter");
            rs = stmt.executeQuery();
            rs.next();
            voterID = rs.getInt(1);
        } catch (SQLException se) {
            Logger.getLogger("VerVoterDAO").log(Level.SEVERE, "broke in setVerVoter, counting voter sql", se);

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
            Logger.getLogger("VerVoterDAO").log(Level.SEVERE, "broke in setVerVoter, inserting voter in", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return true;
    }
}
