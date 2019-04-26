package com.app.dao;

import com.app.entity.Candidate;
import com.app.entity.Rootuser;
import com.app.entity.Voter;
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

/**
 * DAO functions processing table "Rootuser", "voter", "candidate" and "admin" in Q&A system's db
 * as part of data persistence layer.
 * Contain typical CRUD functions.
 */
public class UserDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDAO.class);


    /**
     * Insert a new user record into db
     * before calling this function, pwd should be hashed
     * @param username
     * @param password
     * @param role
     * @return username if insert succeeds, empty string if it fails
     */
    public static String insertUser(String username, String password, int role){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int userID = 0;

        // obtain current max user id
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("select IFNULL(MAX(userid),0) from rootuser");
            rs = stmt.executeQuery();
            rs.next();
            userID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in insertUser, obtain max user id",se);

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
                    "workplace, political_affiliation, political_goal, education, profile_photo_path) " +
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


    /**
     * search for existing user by userid
     * @param id user id
     * @return User object if db query return existing user, null if user does not exist
     */
    public static Rootuser searchUserById (String id) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
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


    /**
     * search for existing user by username
     * @param name username
     * @return User object if db query return existing user, null if user does not exist
     */
    public static Rootuser searchUserByName (String name) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
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
     * Search candidate user table given userid
     * @param userid userid in Rootuser table
     * @return an object of Candidate class
     */
    public static Candidate searchCandidateById (int userid) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Candidate user = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM candidate WHERE userid=\""
                + userid + "\"");
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = extractCandidateFromRS(rs);
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in searchCandidateById",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return user;
    }



    /**
     * Search for all user records requesting account deletion
     * @return a list of Rootuser entity class objects
     */
    public static List<Rootuser> searchUsersByDel () {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
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


    /**
     * Retrieve all voter records from db
     * @return a list of voters
     */
    public static ArrayList<Voter> getAllVoters(){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<Voter> voters = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser LEFT JOIN voter ON rootuser.userid = voter.userid WHERE rootuser.role = 1");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String location = rs.getString(7);
                String email = rs.getString(8);
                Voter voter = new Voter(userid, username, location, email);
                voters.add(voter);
            }

        } catch (SQLException se) {
            logger.error("sql exception in getAllVoters",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return voters;
    }


    /**
     * Search for a voter record by userid
     * @param searchThisUserID user id
     * @return Voter object if it exits, null if not
     */
    public static Voter getVoter(int searchThisUserID){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Voter voter = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser LEFT JOIN voter ON rootuser.userid = voter.userid WHERE rootuser.role = 1 and rootuser.userid = ?");
            stmt.setInt(1,searchThisUserID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String location = rs.getString(7);
                String email = rs.getString(8);
                voter = new Voter(userid, username, location, email);
            }

        } catch (SQLException se) {
            logger.error("sql exception in getAllVoters",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return voter;
    }


    /**
     * Retrieve all candidate records from db
     * @return a list of candidates
     */
    public static ArrayList<Candidate> getAllCandidates(){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<Candidate> candidates = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser LEFT JOIN candidate ON rootuser.userid = candidate.userid WHERE rootuser.role = 2");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt("userid");
                String realname = rs.getString("realname");
                int age = rs.getInt("age");
                String location = rs.getString("location");
                String workplace = rs.getString("workplace");
                String political_affiliation = rs.getString("political_affiliation");
                String political_goal = rs.getString("political_goal");
                String education = rs.getString("education");
                String profilePhoto = rs.getString("profile_photo_path");
                Candidate candidate = new Candidate(userid,realname,age,location,workplace,political_affiliation,political_goal,education,profilePhoto);
                candidates.add(candidate);
            }

        } catch (SQLException se) {
            logger.error("sql exception in getAllCandidates",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return candidates;
    }


    /**
     * Search for candidate record by userid
     * @param userID user id
     * @return an Candidate entity class object if it exists, null if not
     */
    public static Candidate getCandidate(int userID){
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Candidate candidate = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM rootuser LEFT JOIN candidate ON rootuser.userid = candidate.userid WHERE rootuser.role = 2 and candidate.userid = ?");
            stmt.setInt(1,userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt("userid");
                String realname = rs.getString("realname");
                int age = rs.getInt("age");
                String location = rs.getString("location");
                String workplace = rs.getString("workplace");
                String political_affiliation = rs.getString("political_affiliation");
                String political_goal = rs.getString("political_goal");
                String education = rs.getString("education");
                String profilePhoto = rs.getString("profile_photo_path");
                candidate = new Candidate(userid,realname,age,location,workplace,political_affiliation,political_goal,education,profilePhoto);
            }

        } catch (SQLException se) {
            logger.error("sql exception in getCandidate",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return candidate;
    }


    /**
     * Delete voter from both rootuser and voter table
     * @param userid user id
     * @return true if delete succeeds, false if it fails
     */
    public static boolean deleteVoter (int userid) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean result1 = false;
        boolean result2 = deleteUser(userid);

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM voter WHERE userid=" + userid;
            stmt = conn.prepareStatement(sql);

            int sqlResult = stmt.executeUpdate();  // 0 or 1
            if (sqlResult == 1) {
                result1 = true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteVoter", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return result1 && result2;
    }


    /**
     * Delete candidate record from database
     * @param userid
     * @return true if delete succeeds, false if it fails
     */
    public static boolean deleteCandidate (int userid) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean result1 = false;
        boolean result2 = deleteUser(userid);

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM candidate WHERE userid=" + userid;
            stmt = conn.prepareStatement(sql);

            int sqlResult = stmt.executeUpdate();  // 0 or 1
            if (sqlResult == 1) {
                result1 = true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteCandidate", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return result1 && result2;
    }


    /**
     * Delete user record from Rootuser table
     * @param userid
     * @return true if delete succeeds, false if it fails
     */
    public static boolean deleteUser (int userid) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM rootuser WHERE userid=" + userid;
            stmt = conn.prepareStatement(sql);

            int sqlResult2 = stmt.executeUpdate();  // 0 or 1
            if (sqlResult2 == 1) {
                result = true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteUser", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }


    /**
     * Register a new user in db.
     * This function is only used for developer's testing purpose.
     * @param username
     * @param pwd
     * @param role
     * @return
     */
    public static boolean registration (String username, String pwd, int role) {
        // search for existing Rootuser By Name first
        Rootuser user = searchUserByName(username);

        // if current user doesn't exist in db, create one
        if (user == null) {
            // hash pwd
            String salt = BCrypt.gensalt(11);
            String hashed = BCrypt.hashpw(pwd, salt);
            logger.info("hashed pwd: " + hashed);

            return ! insertUser(username, hashed, role).equals("");
        }
        return false;
    }


    /**
     * Transform result set into Candidate object
     * @param rs result set object returned by sql statement
     * @return an object of Candidate entity class
     */
    public static Candidate extractCandidateFromRS (ResultSet rs) throws SQLException {
        Candidate candidate = new Candidate();

        candidate.setUserId(rs.getInt("userid"));
        candidate.setRealname(rs.getString("realname"));
        candidate.setAge(rs.getInt("age"));
        candidate.setLocation(rs.getString("location"));
        candidate.setWorkplace(rs.getString("workplace"));
        candidate.setPoliticalAffiliation(rs.getString("political_affiliation"));
        candidate.setPoliticalGoal(rs.getString("political_goal"));
        candidate.setEducation(rs.getString("education"));
        candidate.setProfilePhotoPath(rs.getString("profile_photo_path"));

        return candidate;
    }


}