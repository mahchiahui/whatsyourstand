package com.app.dao;

import com.app.entity.User;
import com.app.utility.ConnectionManager;
import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.log4j.Priority;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement stmt = null;

    public UserDAO () {
    }


    /**
     * Insert a new user record into db
     * before calling this function, pwd should be hashed
     * @param password
     * @param role
     * @return
     */
    public static String insertUser(String password, int role){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int userID = 0;

        //count number of voters
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("select Count(Distinct userid) from user");
            rs = stmt.executeQuery();
            rs.next();
            userID = rs.getInt(1);
        } catch (SQLException se) {
            Logger.getLogger("UserDAO").log(Level.SEVERE, "broke in insertUser, counting user sql", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        //create new voterID
        userID++;

        //insert user into database
        String sqlUser = "INSERT INTO user (userid, name, hashpwd, role, request_del) VALUES (?,?,?,?,?)";
        String sqlVoter = "INSERT INTO voter (userid, email, location) VALUES (?,?,?)";
        Random rand = new Random();
        String voterName = "voter" + Integer.toString(Math.abs(rand.nextInt()));
        try {
            conn = ConnectionManager.getConnection("14819db");

            stmt = conn.prepareStatement(sqlUser);
            stmt.setInt (1, userID);
            stmt.setString(2, voterName);
            stmt.setString(3, password);
            stmt.setInt(4, role);
            stmt.setInt(5, 0);

            int result = stmt.executeUpdate();
            if (result == 0) {
                return "";
            }

            stmt = conn.prepareStatement(sqlVoter);
            stmt.setInt (1, userID);
            stmt.setString(2, "");
            stmt.setString(3, "");

            result = stmt.executeUpdate();
            if (result == 0) {
                return "";
            }
        } catch (SQLException se) {
            Logger.getLogger("UserDAO").log(Level.SEVERE, "broke in insertUser, inserting user in", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return voterName;
    }


    public static User searchUserById (String id) {
        User user = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM user WHERE userid=\""
                + id + "\"");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);

                if (userid == Integer.parseInt(id)) {
                    user = new User(userid, username, password);
                    break;
                }
            }
        }
        catch (SQLException se) {
//            Logger.getLogger("UserDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("UserDAO").log(Level.SEVERE, null, se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }


    public static User searchUserByName (String name) {
        User user = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM user WHERE name=\""
                + name + "\"");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);

                if (name.equals(username)) {
                    user = new User(userid, username, password);
                    break;
                }

            }
        }
        catch (SQLException se) {
//            Logger.getLogger("UserDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("UserDAO").log(Level.SEVERE, null, se);  // use java.util.Logger for this
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
    public static List<User> searchUsersByDel () {
        List<User> users = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM user WHERE request_del=0");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int role = rs.getInt(4);
                int request_del = rs.getInt(5);

                if (request_del == 1 && (role == 1 || role == 2)) {
                    // can only delete voter or candidate data here
                    users.add(new User(userid, username, password));
                }

            }
        }
        catch (SQLException se) {
//            Logger.getLogger("UserDAO").log(Priority.ERROR, null, se);
            Logger.getLogger("UserDAO").log(Level.SEVERE, null, se);  // use java.util.Logger for this
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return users;
    }

    /**
     * Insert a new user record into db
     * before calling this function, pwd should be hashed
     * @param username
     * @param pwd hashed password
     * @return true if insert succeed, false if username exist
     */
    public static boolean createUser (String username, String pwd, String salt, int role) {
        int curMaxId = 0;
        String userId = null;
        boolean flagSuccess = false;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT COUNT(userid) AS rowcount FROM user");
            rs = stmt.executeQuery();

            rs.next();
            curMaxId = rs.getInt("rowcount");
            System.out.println("curMaxId: " + curMaxId);
            userId = Integer.toString(curMaxId + 1);

            String sqlInsert = "INSERT INTO user (userid, name, hashpwd, role, request_del) VALUES ("
                +  userId + ", '" + username + "', '" + pwd + "', " + Integer.toString(role) + ", 0)";
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
        // search for existing User By Name first
        User user = searchUserByName(username);
        if (user == null) {
            // hash pwd
            String salt = BCrypt.gensalt(11);
            String hashed = BCrypt.hashpw(pwd, salt);
            System.out.println("hashed pwd: " + hashed);
            return createUser(username, hashed, salt, role);
        }
        return false;
    }
}