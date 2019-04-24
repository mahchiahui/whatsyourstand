package com.app.servlet;

import com.app.controller.RedirectController;
import com.app.dao.QuestionDAO;
import com.app.entity.Question;
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
 * Handle question update and delete,
 * need to keep track of the original page so that it could redirect back to the original page
 */
//@WebServlet(name = "QuestionUpdateServlet")
public class QuestionUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questionId = (String) session.getAttribute(Constants.ATTRIBUTE_QUESTION_KEY);
        String lastURL = request.getParameter("lasturl");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String curTime = DateUtil.getCurrentTime();

//        HttpSession session = request.getSession(false);
//        Question question = (Question) session.getAttribute(Constants.ATTRIBUTE_QUESTION_KEY);
        Question question = QuestionDAO.searchQuestionByID(Integer.parseInt(questionId));
        question.setTitle(title);
        question.setDescription(description);
        question.setLocation(location);
        question.setLastModifiedTime(curTime);

        if (QuestionDAO.updateQuestion(question)) {
            response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
            // "/voter-myquestions"
//            RedirectController.showFrontEnd(request, response, "/html/voter-myquestions.jsp");
        }
        else {
            ServletOutputStream out = response.getOutputStream();
            out.println("<div class=\"sidebar-brand-text mx-3\">Please try again.</div>");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String questionid = request.getParameter("questionid");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        session.setAttribute(Constants.ATTRIBUTE_QUESTION_KEY, questionid);

//        System.out.println(questionid);
        if (action.equals("update")) {
            RedirectController.showFrontEnd(request, response, "/html/voter-updatequestion.html");
        }
        else if (action.equals("delete")) {
            QuestionDAO.deleteQuestion(Integer.parseInt(questionid));

            String lastURL = request.getParameter("lasturl");
            response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
        }
    }
}
