package com.app.filter;

import com.app.controller.LoginController;
import com.app.dao.CookieDao;
import com.app.entity.Cookie;
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

        System.out.println("filter catch it");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);
        if (session == null) {   //checking whether the session exists
            this.context.log("Unauthorized access request");
            response.sendRedirect(context.getContextPath() + "/login-voter");
        }

        String cookieid = LoginController.getCookieId(request);
//        System.out.println(cookieid);

        if (cookieid != null) {
            Cookie cookie = CookieDao.searchCookie(cookieid);
            String curTime = DateUtil.getCurrentTime();
            // Cookie is valid and has not expired
            if (cookie != null &&
                DateUtil.isTimeDiffLessThanOneDay(curTime, cookie.getTimestamp())) {
                return;
            }
        }



        /*
        else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }*/
    }

    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

}
