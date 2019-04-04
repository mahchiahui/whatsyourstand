package com.app.servlet;

import com.app.dao.VerVoterDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class VerificationServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "data";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //receive verification parameters
        String name = request.getParameter("full_name");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        //String pNum = request.getParameter("phoneNum");
        String pNum = "4129036789";

        //get upload file path
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;

        //create the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        //upload file onto server, change the file name to the user's full name but with all whitespace taken out and appended with Doc.pdf
        String locationDocumentPath= "";
        for (Part part : request.getParts()) {
            String fileName = name;
            fileName = fileName.replaceAll("\\s+","");
            fileName += "Doc.pdf";
            locationDocumentPath = uploadPath + File.separator + fileName;
            part.write(locationDocumentPath);
        }

        //set up
        boolean insertVoterResult = VerVoterDAO.setVerVoter(pNum, city, locationDocumentPath, email, name);
        request.setAttribute("insertVoterResult", insertVoterResult);
        RequestDispatcher view = request.getRequestDispatcher("/html/register-infosubmitted.html");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/register-proofdoc.html");
        view.forward(request, response);
    }
}
