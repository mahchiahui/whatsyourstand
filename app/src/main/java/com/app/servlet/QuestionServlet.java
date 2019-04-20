package com.app.servlet;

import com.app.dao.QuestionDAO;
import com.app.entity.Rootuser;
import com.app.utility.Constants;
import com.app.utility.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "QuestionServlet")
public class QuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Rootuser user = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String curTime = DateUtil.getCurrentTime();
        QuestionDAO.createNewQuestion(user.getUserId(), title, description, curTime, "Pittsburgh");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
