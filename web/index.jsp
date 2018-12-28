<%--
  Created by IntelliJ IDEA.
  User: cluster
  Date: 12/14/2018
  Time: 9:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    int condition;
%>
<html>
  <head>
    <title>cluster</title>
  </head>
  <body>
  <!-- Thispage was loaded on
  <%= (new java.util.Date()) %>  -->
    你好呀，年轻人

  <%
      String Msg = "This is JSP test";
      out.print("HelloWorld!");
      condition=1;
      switch(condition){
          case 0:
              out.println("You must select condition 0!"+"<br>");break;
          case 1:
              out.println("You must select condition 1!"+"<br>");break;
          case 2:
              out.println("You must select condition 2!"+"<br>");break;
          default:
              out.println("Your select not in \"0,1,2\",select again!!"+"<br>");
      }

  %>

  <form action="requeset.jsp">
      姓名<input type="text" name="UserName">
      <input type="submit" value="提交">
  </form><br>
  <form action="Login_session.jsp">
      姓名<input type="text" name="UserName"/>
      <input type="submit" value="提交"/>
  </form>
  </body>
    <h2><%=Msg%></h2>
</html>
