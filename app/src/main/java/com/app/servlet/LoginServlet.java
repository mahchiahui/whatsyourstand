package com.app.servlet;

import com.app.controller.LoginController;
import com.app.dao.CookieDao;
import com.app.dao.UserDAO;
import com.app.entity.Cookie;
import com.app.entity.User;
import com.app.utility.Constants;
import com.app.utility.DateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "com.app.servlet.LoginServlet")
//@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    response.getWriter().append("Served at: ").append(request.getContextPath());

        // Get HttpSession object
        HttpSession session = request.getSession();
        // setting session to expiry in 60 mins
//        session.setMaxInactiveInterval(60 * 60);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        User user = LoginController.login(response, username, password, 0, true);

        if (user != null) {  // Suppose a user has successfully logged
            System.out.println("Login succeed");
            // Storing user information in an attribute of Session.
            session.setAttribute(Constants.SESSION_USER_KEY, user);
//            String sessionId = session.getId();

            redirect(request, response, user);
//            response.sendRedirect(this.getServletContext().getContextPath() + "/testing");
        }
        else {
            System.out.println("Login failed");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get HttpSession object
        HttpSession session = request.getSession();

        // Get the User object stored to the session
        User loginedInfo = (User) session.getAttribute(Constants.SESSION_USER_KEY);

        // Session does not contain login info, check local storage of cookie
        if (loginedInfo == null) {
            String cookieid = LoginController.getCookieId(request);
            if (cookieid != null) {
                Cookie cookie = CookieDao.searchCookie(cookieid);
                String curTime = DateUtil.getCurrentTime();
                // Cookie is valid and has not expired
                if (cookie != null &&
                    DateUtil.isTimeDiffLessThanOneDay(curTime, cookie.getTimestamp())) {
                    User user = UserDAO.searchUserById(cookie.getUserId());
                    redirect(request, response, user);
                    return;
                }
                // Old local storage needs to be cleared
                LoginController.clearLoginCookie(request, response);
            }

            // If not logined, redirect to login page (LoginServlet).
            RequestDispatcher view = request.getRequestDispatcher("/html/LoginPage.html");
            view.forward(request, response);
        }
        else {  // if logined, go to main page
            redirect(request, response, loginedInfo);
        }
    }


    /**
     * Redirect to the corresponding user account using logged in info
     * @param request
     * @param response
     * @param user
     * @throws ServletException
     * @throws IOException
     */
    public void redirect (HttpServletRequest request, HttpServletResponse response, User user)
        throws ServletException, IOException {

//        int role = Integer.parseInt(request.getParameter("role"));
        int role = user.getRole();
        if (role == 0) {
            response.sendRedirect(this.getServletContext().getContextPath() + "/admin");
//            response.sendRedirect(this.getServletContext().getContextPath() + "/testing");
        }
        else if (role == 1) {
            response.sendRedirect(this.getServletContext().getContextPath() + "/voter");
        }
        else {  // candidate
            response.sendRedirect(this.getServletContext().getContextPath() + "/candidate");
        }
    }
}
