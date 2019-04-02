<%@ page import="java.util.Properties" %>
<%@ page import="com.app.utility.ConnectionManager" %>
<%@ page import="java.io.InputStream" %><%--
  Created by IntelliJ IDEA.
  User: mahch
  Date: 4/2/2019
  Time: 11:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dbtest</title>
</head>
<body>
    dbtest
    <%
        String osName = System.getProperty("os.name");
        out.println("<h1>"+ osName +"</h1>");

    %>
</body>
</html>
