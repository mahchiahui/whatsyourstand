package com.app.dao;

import com.app.entity.Question;
import com.app.entity.ReportedQuestion;
import com.app.entity.Rootuser;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO functions processing table "question" in Q&A system's db
 * as part of data persistence layer.
 * Contain typical CRUD functions.
 */
public class QuestionDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionDAO.class);


    /**
     * Create a new question record in db.
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

        // obtain current max question id
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT IFNULL(MAX(questionid),0) FROM question");
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

        // insert a new question record into db
        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "INSERT INTO question (questionid, userid, title, description, created_time, " +
                "last_mod_time, location, num_answer, upvote, downvote, problematic) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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

            stmt.executeUpdate();

        } catch (SQLException se) {
            logger.error("sql exception in createNewQuestion, inserting question into table", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }


    /**
     * Get the list of questions posted by a given user.
     * @param user an object of Rootuser
     * @return a list of objects of Question entity class
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
                questions.add(question);
            }

        } catch (SQLException se) {
            logger.error("sql exception in readQuestionList",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return questions;
    }


    /**
     * Obtain questions sorted by value of (# of upvote - # of downvote)
     * @return a list of objects of Question entity class
     */
    public static List<Question> readTopQuestionList () {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Question> questions = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM question ORDER BY (upvote - downvote) DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = extractQuestionFromRS(rs);
                questions.add(question);
            }

        } catch (SQLException se) {
            logger.error("sql exception in readTopQuestionList",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return questions;
    }


    /**
     * Update a record in question table, if it exists.
     * @param question an object of Question entity class
     * @return true if update succeeds, false if it fails
     */
    public static boolean updateQuestion (Question question) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");
            String sql = "UPDATE question SET title=?, description=?, last_mod_time=?, location=?, " +
                "num_answer=?, upvote=?, downvote=?, problematic=? WHERE questionid=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString (1, question.getTitle());
            stmt.setString(2, question.getDescription());
            stmt.setString(3, question.getLastModifiedTime());
            stmt.setString(4, question.getLocation());
            stmt.setInt(5, question.getNumberOfAnswer());
            stmt.setInt(6, question.getUpvote());
            stmt.setInt(7, question.getDownvote());
            stmt.setInt(8, question.isFlagProblematic());
            stmt.setInt(9, question.getQuestionId());

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
     * Search for a question using given question id.
     * @param questionid question's id
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
     * @param questionID question's id
     * @return true if delete succeeds, false if it fails
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
     * Delete a reportedQuestion given the question id
     * @param questionID : question's id
     */
    public static boolean deleteReportedQuestion (int questionID) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM report WHERE questionid=" + questionID;
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
     * get all reported question from the database
     * @return a list of reported questions
     */
    public static ArrayList<ReportedQuestion> getAllReportedQuestion() {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<ReportedQuestion> reportedQuestions = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "SELECT question.questionid,title,description,content FROM question LEFT JOIN report ON question.questionid = report.questionid where content is not null";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()){
                int questionID = rs.getInt("questionid");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String reportedIssue = rs.getString("content");
                ReportedQuestion reportedQuestion = new ReportedQuestion(questionID,title,description,reportedIssue);
                reportedQuestions.add(reportedQuestion);
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteQuestion", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return reportedQuestions;
    }

    /**
     * get all reported question from the database
     * @return a list of reported questions
     */
    public static ReportedQuestion getReportedQuestion(int questionid) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ReportedQuestion reportedQuestion = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "SELECT question.questionid,title,description,content FROM question LEFT JOIN report ON question.questionid = report.questionid where content is not null and question.questionid="+questionid;
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()){
                int questionID = rs.getInt("questionid");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String reportedIssue = rs.getString("content");
                reportedQuestion = new ReportedQuestion(questionID,title,description,reportedIssue);
            }

        } catch (SQLException se) {
            logger.error("sql exception in getReportedQuestion", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return reportedQuestion;
    }

    /**
     * Transform result set into Question object
     * @param rs result set object returned by sql statement
     * @return a Question entity object
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
        question.setFlagProblematic(rs.getInt("problematic"));

        return question;
    }
}
