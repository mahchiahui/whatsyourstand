<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Verification Sent</title>
</head>
<body>
<%
    boolean result = (boolean)request.getAttribute("insertVoterResult");
    if (result) {
        out.println("<h1>Verification Sent Successfully</h1>");
    } else {
        out.println("<h1>Verification Failed to Sent</h1>");
    }
%>

</body>
</html>