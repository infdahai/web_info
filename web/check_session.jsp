<%--
  Created by IntelliJ IDEA.
  User: cluster
  Date: 12/14/2018
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String your_name = (String) session.getValue("LogName");
    if (your_name == null) {
%>not logging<%
    } else {
        %>
    "<%=your_name%> "
    <%
    }
%>
</body>
</html>
