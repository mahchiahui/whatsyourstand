package com.app.controller;

import com.app.dao.QuestionDAO;
import com.app.dao.ReportDAO;
import com.app.dao.StatusDAO;
import com.app.dao.UserDAO;
import com.app.entity.Question;
import com.app.entity.Report;
import com.app.entity.Rootuser;
import com.app.entity.Status;

/**
 * Controller class for question's CRUD operations in business logic layer.
 * Call DAO functions in data access layer to manipulate the database.
 * Tables involved in db to support the operations includes: question, report, status, and user.
 */
public class QuestionController {

    /**
     * Mark an question as problematic and create a new record of report in db
     * as long as someone report an issue of a question.
     * @param questionId
     * @param userId
     * @param content
     * @return
     */
    public static void markAsProblematic (int questionId, int userId, String content) {
        Report report = new Report(0, questionId, userId, content);
        Question question = QuestionDAO.searchQuestionByID(questionId);
        question.setFlagProblematic(1);
        QuestionDAO.updateQuestion(question);
        ReportDAO.createReport(report);
    }


    /**
     * Query the db at most twice to decide what status of
     * upvote & downvote to return to the frontend.
     * @param questionId
     * @param userId
     * @return
     */
    public static Status showStatus (int questionId, int userId) {
        // default status is that neither upvote and downvote is marked
        Status finalStatus = new Status(questionId, userId, 0);

        // check if upvote or downvote status exists in db
        if (StatusDAO.checkStatus(new Status(questionId, userId, 1))) {
            finalStatus.setStatusType(1);
            return finalStatus;
        }
        else if (StatusDAO.checkStatus(new Status(questionId, userId, 2))) {
            finalStatus.setStatusType(2);
            return finalStatus;
        }

        // return default status if neither status exists in db
        return finalStatus;
    }


    /**
     * Decide whether or not to add or reduce a upvote, how to set the status type,
     * and update the number of upvote in corresponding question table.
     * @param questionId
     * @param userId
     */
    public static void setUpvote (int questionId, int userId) {
        Status status = showStatus(questionId, userId);
        Question question = QuestionDAO.searchQuestionByID(questionId);
        Rootuser rootuser = UserDAO.searchUserById(String.valueOf(userId));
        int statusType = status.getStatusType();

        switch (statusType) {
            case 0:
                addUpvote(question, rootuser);
                break;
            case 1:
                reduceUpvote(question, rootuser);
                break;
            case 2:
                reduceDownvote(question, rootuser);
                addUpvote(question, rootuser);
                break;
        }
    }


    /**
     * Decide whether or not to add or reduce a downvote, how to set the status type,
     * and update the number of downvote in corresponding question table.
     * @param questionId
     * @param userId
     */
    public static void setDownvote (int questionId, int userId) {
        Status status = showStatus(questionId, userId);
        Question question = QuestionDAO.searchQuestionByID(questionId);
        Rootuser rootuser = UserDAO.searchUserById(String.valueOf(userId));
        int statusType = status.getStatusType();

        switch (statusType) {
            case 0:
                addDownvote(question, rootuser);
                break;
            case 1:
                reduceUpvote(question, rootuser);
                addDownvote(question, rootuser);
                break;
            case 2:
                reduceDownvote(question, rootuser);
                break;
        }
    }


    /**
     * Increase one upvote in certain question.
     * @param question
     * @param user
     */
    public static void addUpvote (Question question, Rootuser user) {
        int upvote = question.getUpvote();
        question.setUpvote(upvote + 1);
        StatusDAO.createStatus(new Status(question.getQuestionId(), user.getUserId(), 1));
        QuestionDAO.updateQuestion(question);
    }


    /**
     * Reduce one upvote in certain question.
     * @param question
     * @param user
     */
    public static void reduceUpvote (Question question, Rootuser user) {
        int upvote = question.getUpvote();
        question.setUpvote(upvote - 1);
        StatusDAO.deleteStatus(new Status(question.getQuestionId(), user.getUserId(), 1));
        QuestionDAO.updateQuestion(question);
    }


    /**
     * Increase one downvote in certain question.
     * @param question
     * @param user
     */
    public static void addDownvote (Question question, Rootuser user) {
        int downvote = question.getDownvote();
        question.setDownvote(downvote + 1);
        StatusDAO.createStatus(new Status(question.getQuestionId(), user.getUserId(), 2));
        QuestionDAO.updateQuestion(question);
    }


    /**
     * Reduce one downvote in certain question.
     * @param question
     * @param user
     */
    public static void reduceDownvote (Question question, Rootuser user) {
        int downvote = question.getDownvote();
        question.setDownvote(downvote - 1);
        StatusDAO.deleteStatus(new Status(question.getQuestionId(), user.getUserId(), 2));
        QuestionDAO.updateQuestion(question);
    }

}
