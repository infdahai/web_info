<%--
  Created by IntelliJ IDEA.
  User: cluster
  Date: 12/14/2018
  Time: 7:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.*" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    你好,
<%! String Name; %>
<%
    Name = request.getParameter("UserName");
%>
<%=Name%>
</body>
</html>
