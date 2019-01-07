<%@ page import = "lucene.SearchBuilder, java.util.Map, java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Queue" %>
<%@ page import="java.util.concurrent.ArrayBlockingQueue" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%
    String queryText, queryPage, queryNext, queryLast, searchType, sortType;
    String strHistory;
    Integer hisMaxCnt = 7;
    request.setCharacterEncoding("UTF-8");
    queryText = request.getParameter("query");
    queryPage = request.getParameter("page");
    queryLast = request.getParameter("last");
    strHistory = request.getParameter("history");
    searchType = request.getParameter("searchType");
    sortType = request.getParameter("sortType");
    if(strHistory == null)
        strHistory = "    ";
    if(strHistory != null && !strHistory.equals("null") && queryText != null && !strHistory.contains(queryText)) {
        String[] his = strHistory.split(" ");
        if(his.length > hisMaxCnt) {
            strHistory = "";
            for (int i = 1; i < hisMaxCnt; i++)
                strHistory = strHistory+"  "+his[i];
        }
        strHistory += "  "+queryText;

    }
    if(queryPage == null || queryPage.equals(""))
        queryPage = "1";

%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%= queryText %> - 咸鱼的搜索引擎</title>
</head>

<body>

<form method = "POST" action = "result.jsp">
    <p>
        <font color = "black" size = "4">&nbsp&nbsp搜索内容： </font>
        <select name = "searchType">
            <option value ="subject">标题相关</option>
            <option value="topics">主题相关</option>
            <option value ="location">地点相关</option>
        </select>
        <font color = "black" size = "4">&nbsp&nbsp排序方式： </font>
        <select name = "sortType">
            <option value="RELEVANCE">相关度优先</option>
            <option value ="INDEXORDER">索引序优先</option>
        </select>
        <font size = "4" face="Microsoft YaHei" color = "#ffb6c1">&nbsp&nbsp咸鱼一下√</font>

    </p>
    <input type = "text" name = "query" style = "width:400px;height:40px" value="<%= queryText %>">
    <input type = "submit" style = "width:100px;" value = "搜索并获取第" style = "width:80px;height:40px">
    <input type = "text" name = "page" style = "width:50px;height:40px" oninput="value=value.replace(/[^\d]/g,'')" value="<%= queryPage %>" >
    <input type = "hidden" name = "history" value="<%= strHistory %>" >
    <input type = "hidden" name = "last" value="<%= queryText %>" >
    页
</form>
<br><br>
<%
    System.out.println(queryText);
    boolean flag = false;
    //ArrayList<Document> docs,results = new ArrayList<Document>();
    ArrayList<Map<String,String>> docs = new ArrayList<>(),results=new ArrayList<>();
    int Page = 1, size = 0;
    SearchBuilder search = new SearchBuilder();
    if( queryPage != null && queryPage.length()!=0)
        Page = Integer.parseInt(queryPage);
    if(queryText != null && queryText != "") {
        try {
            docs = search.doSearch(queryText, searchType, sortType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        size = docs.size();
        out.println("<font color = \"green\" size = \"1\">" + "找到了 " + size + " 个结果 ");
        out.println("<font size = \"1\">" + "每页最多显示15条，当前是第 " + Page + " 页，一共" + (size / 15 + 1) + "页");
        out.println("</font>" + "<br><br>");
        if(size > (Page-1)*15 && Page > 0)
            for (int i = 0; i < 15; i++) {
                if((Page-1)*15+i < size)
                    results.add(docs.get((Page-1)*15+i));
            }
        else if(size != 0){
            out.println("<font color = \"red\" size = \"1\">");
            out.print("抱歉,超出了页数范围: 1 - " + (size/15+1));
            out.println("</font>" + "<br>");
            flag = true;
        }
        if(results != null && results.size() != 0 ){
            String strFrom, strTo, strUrl, strSubject, strDeadline, strLocation, strTopics;
            for(int i = 0 ; i < results.size() ; i ++){
                Map<String,String> doc = results.get(i);

                strFrom = doc.get("from");
                strTo = doc.get("to");
                strUrl = doc.get("description");
                strSubject = doc.get("subject");
                strDeadline = doc.get("deadline");
                strLocation = doc.get("location");
                strTopics = doc.get("topics");
                if(strTopics.length()>100) strTopics = strTopics.substring(0,100) + "...";

                out.println("<font color = \"blue\" size = \"3\">");
                out.print("<a href=\"" + strUrl + "\">" + strSubject + "</a>");
                out.println("</font>" + "<br>");
                out.println("<font color = \"black\" size = \"1\">" + " <strong>从</strong>: " + strFrom +  " <strong>到</strong>: " + strTo);
                out.println("</font>" + "<br>");
                out.println("<font color = \"black\" size = \"1\">" + "<strong>截止时间</strong>: " + strDeadline);
                out.println("</font>" + "<br>");
                out.println("<font color = \"black\" size = \"1\">" + " <strong>地点</strong>: " + strLocation);
                out.println("</font>" + "<br>");
                out.println("<font color = \"black\" size = \"1\">" + " <strong>主题</strong>: " + strTopics + "<br>" + "<br>");
            }
            out.println("<br>");
        }
        else if(flag == false){
            out.println("<font color = \"red\" size = \"1\">");
            out.print("抱歉,没有找到" + queryText);
            out.println("</font>" + "<br>");
        }
        out.println("搜索记录:<br>");
        out.println("<font color = \"purple\" size = \"3\">");
        out.println(strHistory);
        out.println("</font>" + "<br>");
    }
    else {%>
<script type="text/javascript">
    window.location="index.jsp";
</script>
<%  };
%>


</body>
</html>
