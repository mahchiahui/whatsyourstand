package com.app.dao;

import com.app.entity.Rootuser;
import com.app.utility.ConnectionManager;
//import org.apache.log4j.Logger;
//import org.apache.log4j.Priority;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDAO {

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement stmt = null;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public UserDAO () {
    }


    /**
     * Insert a new user record into db
     * before calling this function, pwd should be hashed
     * @param password
     * @param role
     * @return
     */
    public static String insertUser(String username, String password, int role){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int userID = 0;

        //count number of voters
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("select COUNT(DISTINCT userid) from rootuser");
            rs = stmt.executeQuery();
            rs.next();
            userID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in insertUser, counting user sql",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        //create new voterID
        userID++;

        try {
            conn = ConnectionManager.getConnection("14819db");

            // Insert a user into 3 different user role table
            if (role == 1) {
                String sqlVoter = "INSERT INTO voter (userid, email, location) VALUES (?,?,?)";
                stmt = conn.prepareStatement(sqlVoter);
                stmt.setInt (1, userID);
                stmt.setString(2, "");
                stmt.setString(3, "");

                //create new username
                Random rand = new Random();
                username = "voter" + Math.abs(rand.nextInt());
            }
            else if (role == 0) {
                String sqlAdmin = "INSERT INTO admin (userid, admin_level) VALUES (?,?)";
                stmt = conn.prepareStatement(sqlAdmin);
                stmt.setInt (1, userID);
                stmt.setInt (2, 1);  // now it's default 1
            }
            else {
                String sqlCandidate = "INSERT INTO candidate (userid, realname, age, location, " +
                    "workplace, political_affiliation, political_goal, educationprofile_photo_path) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sqlCandidate);
                stmt.setInt (1, userID);
                stmt.setString(2, "");
                stmt.setInt (3, 18);  // now it's default 18
                stmt.setString(4, "Pittsburgh");
                stmt.setString(5, "");
                stmt.setString(6, "");
                stmt.setString(7, "");
                stmt.setString(8, "");
                stmt.setString(9, "");
            }

            int result = stmt.executeUpdate();
            if (result == 0) {
                return "";
            }

        } catch (SQLException se) {
            logger.error("sql exception in insertUser, inserting user in separate role table",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        try {
            conn = ConnectionManager.getConnection("14819db");
            //insert user into database
            String sqlUser = "INSERT INTO rootuser (userid, username, hashpwd, role, request_del) VALUES (?,?,?,?,?)";

            // Insert a user into Rootuser table
            stmt = conn.prepareStatement(sqlUser);
            stmt.setInt (1, userID);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setInt(4, role);
            stmt.setInt(5, 0);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return "";
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in insertUser, inserting user in rootuser table",se);
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return username;
    }


    public static Rootuser searchUserById (String id) {
        Rootuser user = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser WHERE userid=\""
                + id + "\"");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int role = Integer.parseInt(rs.getString(4));
                int del = Integer.parseInt(rs.getString(5));

                if (userid == Integer.parseInt(id)) {
                    user = new Rootuser(userid, username, password, role, del);
                    break;
                }
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in searchUserById",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }


    public static Rootuser searchUserByName (String name) {
        Rootuser user = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser WHERE username=\""
                + name + "\"");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int role = Integer.parseInt(rs.getString(4));
                int del = Integer.parseInt(rs.getString(5));

                if (name.equals(username)) {
                    user = new Rootuser(userid, username, password, role, del);
                    break;
                }

            }
        }
        catch (SQLException se) {
            logger.error("sql exception in error in searchUserByName",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }


    /**
     * Search for all user records requesting account deletion
     * @return
     */
    public static List<Rootuser> searchUsersByDel () {
        List<Rootuser> users = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser WHERE request_del=0");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int role = rs.getInt(4);
                int request_del = rs.getInt(5);

                if (request_del == 1 && (role == 1 || role == 2)) {
                    // can only delete voter or candidate data here
                    users.add(new Rootuser(userid, username, password));
                }

            }
        }
        catch (SQLException se) {
            logger.error("sql exception in error in searchUsersByDel",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return users;
    }


    public static boolean updateUser () {

        return false;
    }

    /**
     * Change existing password for a user
     * @param username
     * @param oldPassword
     * @param newPassword
     * @param confirmNewPassword
     */
    public static void changePassword (String username, String oldPassword, String newPassword, String confirmNewPassword) {

    }
    public static boolean registration (String username, String pwd, int role) {
        // search for existing Rootuser By Name first
        Rootuser user = searchUserByName(username);
        if (user == null) {
            // hash pwd
            String salt = BCrypt.gensalt(11);
            String hashed = BCrypt.hashpw(pwd, salt);
            System.out.println("hashed pwd: " + hashed);

//            return createUser(username, hashed, role);
            return ! insertUser(username, hashed, role).equals("");
        }
        return false;
    }
}