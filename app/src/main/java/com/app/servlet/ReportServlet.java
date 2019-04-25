package com.app.servlet;

import com.app.controller.RedirectController;
import com.app.dao.ReportDAO;
import com.app.entity.Report;
import com.app.entity.Rootuser;
import com.app.utility.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "ReportServlet")
public class ReportServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String questionId = (String) session.getAttribute(Constants.ATTRIBUTE_QUESTION_KEY);
        Rootuser loginedInfo = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY);
        int userId = loginedInfo.getUserId();
        String content = request.getParameter("content");
        String lastURL = (String) session.getAttribute(Constants.CALLBACK_URL_KEY); // voter-myquestions

        Report report = new Report(0, Integer.parseInt(questionId), userId, content);
        ReportDAO.createReport(report);
        response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String questionid = request.getParameter("questionid");
        String lastURL = request.getParameter("lasturl");

        HttpSession session = request.getSession();
        session.setAttribute(Constants.ATTRIBUTE_QUESTION_KEY, questionid);
        session.setAttribute(Constants.CALLBACK_URL_KEY, lastURL);

        System.out.println(questionid);
        RedirectController.showFrontEnd(request, response, "/html/voter-report.html");
    }
}
