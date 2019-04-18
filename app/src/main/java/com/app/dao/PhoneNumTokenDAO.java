package com.app.dao;

import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneNumTokenDAO {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PhoneNumTokenDAO.class);

    /**
     * inserts phone number token into database
     * @param token
     * @return true or false
     */
    public static boolean insertPhoneNumToken(String token){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean result = false;

        //insert token into database
        String sql = "INSERT INTO phonenumtoken (token) VALUES (?)";

        try {
            conn = ConnectionManager.getConnection("verification");

            stmt = conn.prepareStatement(sql);
            stmt.setString (1, token);

            int sqlResult = stmt.executeUpdate();
            if(sqlResult != 0){
                result = true;
            }
        } catch (SQLException se) {
            logger.error("inserting token in to db",se);
            return false;

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }

    /**
     * checks if the phone number token exists
     * @param token
     * @return result of the sql query
     */
    public static boolean checkPhoneNumToken(String token){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            conn = ConnectionManager.getConnection("verification");

            stmt = conn.prepareStatement("select * from phonenumtoken");
            rs = stmt.executeQuery();
            while (rs.next()) {
                String dbToken = rs.getString(1);
                if(token.equals(dbToken)){
                    result = true;
                }
            }
        } catch (SQLException se) {
            logger.error("retrieving token from verification",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
}
