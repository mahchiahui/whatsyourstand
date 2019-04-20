package com.app.dao;

import com.app.entity.Answer;
import com.app.entity.Question;
import com.app.utility.ConnectionManager;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AnswerDAO.class);

    /**
     * Create a new answer in db
     * @param answer
     */
    public static void createNewAnswer (Answer answer) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int answerID = 0;

        // count number of questions
        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("select COUNT(DISTINCT answerid) from answer");
            rs = stmt.executeQuery();
            rs.next();
            answerID = rs.getInt(1);
        } catch (SQLException se) {
            logger.error("sql exception in insertQuestion, counting question sql", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        // create new questionID
        answerID++;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "INSERT INTO answer (answerid, questionid, userid, content, created_time, " +
                "last_mod_time, upvote, downvote) " +
                "VALUES (?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt (1, answer.getAnswerId());
            stmt.setInt (2, answer.getQuestionId());
            stmt.setInt(3, answer.getUserId());
            stmt.setString(4, answer.getContent());
            stmt.setString(5, answer.getCreatedTime());
            stmt.setString(6, answer.getLastModifiedTime());
            stmt.setInt(7, 0);
            stmt.setInt(8, 0);

            stmt.executeUpdate();  // int result =

        } catch (SQLException se) {
            logger.error("sql exception in createNewAnswer, inserting answer into table", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
    }


    /**
     * Obtain a list of answer attached to a certain question
     * @param question : given question object to read questionID
     * @return a list of Answer objects
     */
    public static List<Answer> readAnswerList (Question question) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        List<Answer> answers = new ArrayList<>();
        int questionID = question.getQuestionId();

        try {
            conn = ConnectionManager.getConnection("14819db");
            stmt = conn.prepareStatement("SELECT * FROM answer WHERE questionid=?");
            stmt.setInt(1, questionID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Answer answer = extractAnswerFromRS(rs);
                if (answer.getQuestionId() == questionID) {
                    answers.add(answer);
                }
            }
            System.out.println(answers);

        } catch (SQLException se) {
            logger.error("sql exception in readAnswerList",se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }

        return answers;
    }


    /**
     * Update a record in answer table, if it exists
     * @param answer
     */
    public static boolean updateAnswer (Answer answer) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "UPDATE answer SET content=?, last_mod_time=?, upvote=?, downvote=? WHERE answerid=?";

            stmt = conn.prepareStatement(sql);
            stmt.setString (1, answer.getContent());
            stmt.setString(2, answer.getLastModifiedTime());
            stmt.setInt(3, answer.getUpvote());
            stmt.setInt(4, answer.getDownvote());
            stmt.setInt(5, answer.getAnswerId());

            int result = stmt.executeUpdate();  // 0 or 1
            if (result == 1) {
                return true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in updateAnswer", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return false;
    }


    /**
     * Delete a answer given the answer id
     * @param answerID : answer's id
     */
    public static boolean deleteAnswer (int answerID) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.getConnection("14819db");

            String sql = "DELETE FROM answer WHERE answerid=" + answerID;
            stmt = conn.prepareStatement(sql);

            int result = stmt.executeUpdate();  // 0 or 1
            if (result == 1) {
                return true;
            }

        } catch (SQLException se) {
            logger.error("sql exception in deleteAnswer", se);

        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return false;
    }


    /**
     * Transform result set into Answer object
     * @param rs
     * @return
     * @throws SQLException
     */
    private static Answer extractAnswerFromRS (ResultSet rs) throws SQLException {
        Answer answer = new Answer();

        answer.setAnswerId(rs.getInt("answerid"));
        answer.setQuestionId(rs.getInt("questionid"));
        answer.setUserId(rs.getInt("userid"));
        answer.setContent(rs.getString("content"));
        answer.setCreatedTime(rs.getString("created_time"));
        answer.setLastModifiedTime(rs.getString("last_mod_time"));
        answer.setUpvote(rs.getInt("upvote"));
        answer.setDownvote(rs.getInt("downvote"));

        return answer;
    }
}
