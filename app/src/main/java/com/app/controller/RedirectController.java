package com.app.controller;

import com.app.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectController {

    /**
     * Link current front-end
     * @param request
     * @param response
     * @param filePath
     * @throws ServletException
     * @throws IOException
     */
    public static void showFrontEnd (HttpServletRequest request, HttpServletResponse response,
            String filePath)
        throws ServletException, IOException {
            RequestDispatcher view = request.getRequestDispatcher(filePath);
            view.forward(request, response);
    }



    public static void showHomePage (HttpServletRequest request, HttpServletResponse response, int role)
        throws ServletException, IOException {

        String strHtml = null;
        switch (role) {
            case 1:
                strHtml = "/html/login-voter.html";
                break;
            case 2:
                strHtml = "/html/login-candidate.html";
                break;
            default:
                strHtml = "/html/login-admin.html";
        }

        // If not logined, redirect to login page (LoginServlet).
        RequestDispatcher view = request.getRequestDispatcher(strHtml);
        view.forward(request, response);
    }


    /**
     * Redirect to the corresponding homepage using logged in info
     * @param request
     * @param response
     * @param user
     * @throws ServletException
     * @throws IOException
     */
    public static void redirectToHomePage (HttpServletRequest request, HttpServletResponse response, User user)
        throws ServletException, IOException {

        int role = user.getRole();
        String strURI = null;
        switch (role) {
            case 1:
                strURI = "/voter";
                break;
            case 2:
                strURI = "/candidate";
                break;
            default:
                strURI = "/admin";;
        }
        response.sendRedirect(request.getServletContext().getContextPath() + strURI);
    }


    /**
     * Redirect to voter's login page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void redirectToLoginPage (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String strURI = "/login-voter";
        response.sendRedirect(request.getServletContext().getContextPath() + strURI);
    }
}
