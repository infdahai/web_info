
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>咸鱼的搜索引擎</title>
</head>
<body>
<br><br>
<form method = "POST" action = "result.jsp">
    <p align = "center"><font size = "12" face="Microsoft YaHei" color = "#ffb6c1">咸鱼一下</font></p><br><br>
    <p align = "center">
        <font size = "12">
            <input type = "text" name = "query" style = "width:400px;height:40px" id="kw"><input type = "submit" value = "搜索" style = "width:80px;height:40px" id="su">
        </font>
    </p>
    <p align = "center">
        <font color = "black" size = "4">"搜索内容： "</font>
        <select name = "searchType">
            <option value ="subject">标题相关</option>
            <option value="topics">主题相关</option>
            <option value ="location">地点相关</option>
        </select>
        <font color = "black" size = "4">"排序方式： "</font>
        <select name = "sortType">
            <option value="RELEVANCE">相关度优先</option>
            <option value ="INDEXORDER">索引序优先</option>
        </select>
    </p>
</form>

<style>
    body
    {
        background:url(./green.png);
        background-size:100% 100%;
        background-repeat:no-repeat;
        padding-top:80px;
    }
</style>
</body>
</html>

