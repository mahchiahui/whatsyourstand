package com.app.servlet;

import com.app.dao.AnswerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles HTTP request on url "/deleteCandidateAnswer" for candidate's page.
 * doGet function handles candidate answer deletion. Answer id are passed as a string.
 * After deletion, redirect to candidate's main page.
 */
public class CandidateDeleteAnswerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String answerID = request.getParameter("answerID");
        AnswerDAO.deleteAnswer(Integer.parseInt(answerID));
        response.sendRedirect(request.getContextPath() + "/candidate");
    }
}
