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

/**
 * This servlet handles HTTP request for url "/question" for creating new question.
 * doPost function handles new question form submission. It inserts new question into db and
 * refresh the voter page by keeping track of whichever previous url is.
 */
public class QuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Rootuser user = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY);
        String lastURL = request.getParameter("lasturl");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String curTime = DateUtil.getCurrentTime();
        QuestionDAO.createNewQuestion(user.getUserId(), title, description, curTime, "Pittsburgh");

        response.sendRedirect(request.getServletContext().getContextPath() + "/" + lastURL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
