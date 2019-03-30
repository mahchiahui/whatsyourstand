<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration Success</title>
</head>
<body>
<%
    boolean result = (boolean)request.getAttribute("insertUserResult");
    if (result) {
        out.println("<h1>Successfully Registered</h1>");
    } else {
        out.println("<h1>Registration failed</h1>");
    }
%>

</body>
</html>