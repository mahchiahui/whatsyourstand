package com.app.servlet;

import com.app.controller.QuestionController;
import com.app.controller.RedirectController;
import com.app.dao.UserDAO;
import com.app.entity.Rootuser;
import com.app.entity.Voter;
import com.app.utility.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet handles HTTP request for question report page.
 * Needs to keep track of which previous url was it before seeing this page,
 * so that when user submit a report form, we can redirect them back.
 */
public class ReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the question id, user id, and previous url from session
        HttpSession session = request.getSession();
        String questionId = (String) session.getAttribute(Constants.ATTRIBUTE_QUESTION_KEY);
        Rootuser loginedInfo = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY);
        int userId = loginedInfo.getUserId();
        String lastURL = (String) session.getAttribute(Constants.CALLBACK_URL_KEY); // voter-myquestions

        // get report content from POST request form data
        String content = request.getParameter("content");

        // insert report into db
        QuestionController.markAsProblematic(Integer.parseInt(questionId), userId, content);

        // redirect to previous url's page
        response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // keep track of which question user clicks on and what was the previous url
        // by getting parameters in query string
        String questionid = request.getParameter("questionid");
        String lastURL = request.getParameter("lasturl");

        // store the value into session once we load the report page
        HttpSession session = request.getSession();
        session.setAttribute(Constants.ATTRIBUTE_QUESTION_KEY, questionid);
        session.setAttribute(Constants.CALLBACK_URL_KEY, lastURL);

        // obtain voter information
        Rootuser loginedInfo = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY);
        Voter voter = UserDAO.getVoter(loginedInfo.getUserId());
        request.setAttribute("voter", voter);

        // show front-end page
        RedirectController.showFrontEnd(request, response, "/html/voter-report.jsp");
    }
}
