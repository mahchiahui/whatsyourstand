<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration Success</title>
</head>
<body>
<%
    String voterUsername = (String)request.getAttribute("voterUsername");
    boolean userInsertSuccess = (boolean)request.getAttribute("userInsertSuccess");
    if (userInsertSuccess) {
        out.println("<h1>Your username is " + voterUsername + "</h1>");
        out.println("<h1>Successfully Registered</h1>");
    } else {
        out.println("<h1>Registration failed</h1>");
    }
%>

</body>
</html>