<%--
  Created by IntelliJ IDEA.
  User: cluster
  Date: 12/14/2018
  Time: 7:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.*" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        String Name = request.getParameter("UserName");
        session.putValue("LogName",Name);
    %>
    你的名字"<%=Name%>"进入session<br>
    <a href="check_session.jsp">check</a>
</body>

</html>
