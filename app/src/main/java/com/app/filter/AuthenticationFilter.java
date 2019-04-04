package com.app.filter;

import com.app.controller.LoginController;
import com.app.dao.CookieDao;
import com.app.dao.UserDAO;
import com.app.entity.Cookie;
import com.app.entity.User;
import com.app.utility.Constants;
import com.app.utility.DateUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        boolean isCookieValid = false;
        String cookieid = LoginController.getCookieId(request);
        User user = null;
        if (cookieid != null) {
            Cookie cookie = CookieDao.searchCookie(cookieid);

            String curTime = DateUtil.getCurrentTime();
            // Cookie is valid in db and has not expired
            if (cookie != null &&
                DateUtil.isTimeDiffLessThanOneDay(curTime, cookie.getTimestamp())) {
                isCookieValid = true;

                String userid = cookie.getUserId();
                user = UserDAO.searchUserById(userid);
            }
        }


        HttpSession session = request.getSession(false);

        boolean isLoggedIn = isCookieValid || (session != null &&
            session.getAttribute(Constants.SESSION_USER_KEY) != null);
        System.out.println("isLoggedIn: " + isLoggedIn);

        String loginURI = request.getContextPath() + "/login";
        String curURI = request.getRequestURI();
        boolean isLoginRequest = false;
        if (request.getRequestURI().length() >= 6) {
            isLoginRequest = curURI.substring(0, 6).equals(loginURI);
        }

//        boolean isRoleCorrect = false;




        if (isLoggedIn && isLoginRequest) {
            // the user is already logged in and he's trying to login again
            // then redirect him to the homepage
            String uriHomePage = null;
            switch (user.getRole()) {
                case 0:
                    uriHomePage = "/admin";
                    break;
                case 1:
                    uriHomePage = "/voter";
                    break;
                default:
                    uriHomePage = "/candidate";
                    break;
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(uriHomePage);
            dispatcher.forward(request, response);

        } else if (isLoggedIn || isLoginRequest) {
            // continues the filter chain
            // allows the request to reach the destination
            chain.doFilter(request, response);

        } else {
            // the user is not logged in, so authentication is required
            // forwards to the Login page
//            response.sendRedirect(context.getContextPath() + "/login");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login-voter");
            dispatcher.forward(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

}
