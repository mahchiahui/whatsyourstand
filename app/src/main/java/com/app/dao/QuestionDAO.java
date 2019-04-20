package com.app.dao;

import com.app.entity.Question;
import com.app.entity.Rootuser;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionDAO.class);


    /**
     * Create a new question record in db
     * @param userID
     * @param title
     * @param description
     * @param timestamp
     * @param location
     */
    public static void createNewQuestion (int userID, String title, String description, String timestamp, String location) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int questionID = 0;

        // count number of questions
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT COUNT(DISTINCT questionid) FROM question");
            rs = stmt.executeQuery();
            rs.next();
            questionID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in createNewQuestion, counting question sql", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        // create new questionID
        questionID++;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "INSERT INTO question (questionid, userid, title, description, created_time, " +
                "last_mod_time, location, num_answer, upvote, downvote, likes, problematic) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, questionID);
            stmt.setInt (2, userID);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, timestamp);
            stmt.setString(6, timestamp);
            stmt.setString(7, location);
            stmt.setInt(8, 0);
            stmt.setInt(9, 0);
            stmt.setInt(10, 0);
            stmt.setInt(11, 0);
            stmt.setInt(12, 0);

            stmt.executeUpdate();  // int result =

        } catch (SQLException se) {
            logger.error("sql exception in createNewQuestion, inserting question into table", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }


    /**
     * Union table user and question to get the list of question posted by certain user
     * @param user
     * @return
     */
    public static List<Question> readQuestionList (Rootuser user) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        int userID = user.getUserId();
        List<Question> questions = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM question WHERE userid=?");
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = extractQuestionFromRS(rs);
                if (question.getUserId() == userID) {
                    questions.add(question);
                }
            }

        } catch (SQLException se) {
            logger.error("sql exception in readQuestionList",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return questions;
    }


    /**
     * Obtain questions with the most number of
     * @return
     */
    public static List<Question> readTopQuestionList (int threshold) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Question> questions = new ArrayList<>();
        int cnt = 0;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM question ORDER BY likes DESC limit ?");
            stmt.setInt(1, threshold);
            rs = stmt.executeQuery();
            while (rs.next()) {
//                if (cnt >= threshold) {
//                    break;
//                }
                Question question = extractQuestionFromRS(rs);
                questions.add(question);
                cnt++;
            }

        } catch (SQLException se) {
            logger.error("sql exception in readQuestionList",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return questions;
    }


    /**
     * Update a record in question table, if it exists
     * @param question
     */
    public static boolean updateQuestion (Question question) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            /*
            String sql = "INSERT INTO question (questionid, userid, title, description, created_time, " +
                "last_mod_time, location, num_answer, upvote, downvote, likes, problematic) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
             */

            String sql = "UPDATE question SET title=?, description=?, last_mod_time=?, location=?" +
                "num_answer=?, upvote=?, downvote=?, likes=?, problematic=? WHERE questionid=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString (1, question.getTitle());
            stmt.setString(2, question.getDescription());
            stmt.setString(3, question.getLastModifiedTime());
            stmt.setString(4, question.getLocation());
            stmt.setInt(5, question.getNumberOfAnswer());
            stmt.setInt(6, question.getUpvote());
            stmt.setInt(7, question.getDownvote());
            stmt.setInt(8, question.getLikes());
            stmt.setInt(9, question.isFlagProblematic());
            stmt.setInt(10, question.getQuestionId());

            int result = stmt.executeUpdate();  // 0 or 1
            if (result == 1) {
                return true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in updateQuestion", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return false;
    }


    /**
     * Search for a question using given question id
     * @param questionid : question's id
     * @return an object of entity class Question
     */
    public static Question searchQuestionByID (int questionid) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Question question = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM question WHERE questionid=" + questionid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                question = extractQuestionFromRS(rs);
            }
        }
        catch (SQLException se) {
            logger.error("sql exception in searchQuestionByID",se);
        }
        finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return question;
    }

    /**
     * Delete a question given the question id
     * @param questionID : question's id
     */
    public static boolean deleteQuestion (int questionID) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM question WHERE questionid=" + questionID;
            stmt = conn.prepareStatement(sql);

            int result = stmt.executeUpdate();  // 0 or 1
            if (result == 1) {
                return true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteQuestion", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return false;
    }


    /**
     * Transform result set into Question object
     * @param rs
     * @return
     * @throws SQLException
     */
    private static Question extractQuestionFromRS (ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setQuestionId(rs.getInt("questionid"));
        question.setUserId(rs.getInt("userid"));
        question.setTitle(rs.getString("title"));
        question.setDescription(rs.getString("description"));
        question.setCreatedTime(rs.getString("created_time"));
        question.setLastModifiedTime(rs.getString("last_mod_time"));
        question.setLocation(rs.getString("location"));
        question.setNumberOfAnswer(rs.getInt("num_answer"));
        question.setUpvote(rs.getInt("upvote"));
        question.setDownvote(rs.getInt("downvote"));
        question.setLikes(rs.getInt("likes"));
        question.setFlagProblematic(rs.getInt("problematic"));

        return question;
    }
}
