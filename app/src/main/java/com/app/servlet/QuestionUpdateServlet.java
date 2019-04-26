package com.app.servlet;

import com.app.controller.RedirectController;
import com.app.dao.QuestionDAO;
import com.app.dao.UserDAO;
import com.app.entity.Question;
import com.app.entity.Rootuser;
import com.app.entity.Voter;
import com.app.utility.Constants;
import com.app.utility.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet handles HTTP request for url "/question-update" for question update and delete.
 * It needs to keep track of the original page so that it could redirect back to the original page.
 * doPost function handles updated question form submission. It updates question record in db and
 * if it succeeds, redirect user to the previous voter page.
 * doGet function keeps track of whichever previous url is, is it an update or delete action,
 * what's the id of question card that is clicked on, store these information into session,
 * and then display the page using jsp.
 */
public class QuestionUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questionId = (String) session.getAttribute(Constants.ATTRIBUTE_QUESTION_KEY);
        String lastURL = (String) session.getAttribute(Constants.CALLBACK_URL_KEY);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String curTime = DateUtil.getCurrentTime();

        Question question = QuestionDAO.searchQuestionByID(Integer.parseInt(questionId));
        question.setTitle(title);
        question.setDescription(description);
        question.setLocation(location);
        question.setLastModifiedTime(curTime);

        if (QuestionDAO.updateQuestion(question)) {
            response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
        }
        else {
            ServletOutputStream out = response.getOutputStream();
            out.println("<div class=\"sidebar-brand-text mx-3\">Please try again.</div>");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // obtain parameters from query string
        String questionid = request.getParameter("questionid");
        String action = request.getParameter("action");
        String lastURL = request.getParameter("lasturl");

        // store previous page's information into session
        HttpSession session = request.getSession();
        session.setAttribute(Constants.ATTRIBUTE_QUESTION_KEY, questionid);
        session.setAttribute(Constants.CALLBACK_URL_KEY, lastURL);
        Rootuser loginedInfo = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY);
        Voter voter = UserDAO.getVoter(loginedInfo.getUserId());
        request.setAttribute("voter", voter);

        // redirect to corresponding pages
        if (action.equals("update")) {
            RedirectController.showFrontEnd(request, response, "/html/voter-updatequestion.jsp");
        }
        else if (action.equals("delete")) {
            QuestionDAO.deleteQuestion(Integer.parseInt(questionid));
            response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
        }
    }
}
