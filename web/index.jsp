<%--
  Created by IntelliJ IDEA.
  User: cluster
  Date: 12/14/2018
  Time: 9:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <!--link rel="stylesheet" type="text/css" href="D://bootstrap-4.2.1-dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="D://bootstrap-4.2.1-dist/css/bootstrap-theme.css"-->
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <title>cluster</title>
</head>
<body>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<div id="logo1"><a href="https://research.cs.wisc.edu/dbworld/browse.html" rel="top">
    <img src="http://dbis-group.uni-muenster.de/dbms/lib/images/dbis-group.jpg"
         alt="Logo dbWorld Search" height="70" class="logo"/>
</a></div>

<table border="0" style="text-align:center;margin-left:auto;margin-right:auto;">
    <tr>
        <td valign="middle">
            <form action="http://dbis-group.uni-muenster.de/dbms/templates/conferences/conferences.php" method="get">
                <div>
                    <input type="text" name="searchTerm" size="20" maxlength="20" value="" />
                    <input type="hidden" name="sortBy" value="start" />
                    <input type="hidden" name="sortDirection" value="" />
                    <select name="dateRange">
                        <option label="all"      value="all"      >all</option>
                        <option label="previous" value="previous" >previous</option>
                        <option label="upcoming" value="upcoming"  selected="selected" >upcoming</option>
                    </select>
                    <input type="submit" name="button_search" value="Search" />
                </div>
            </form>
        </td>
        <td valign="middle" style="padding-left:20px;">
            <form action="http://dbis-group.uni-muenster.de/dbms/templates/conferences/conferences.php" method="get">
                <div>
                    <input type="submit" name="button_all_upcoming" value="Upcoming Confs" />
                    <input type="hidden" name="searchTerm" value="" />
                    <input type="hidden" name="sortBy" value="fromdate" />
                    <input type="hidden" name="sortDirection" value="" />
                    <input type="hidden" name="dateRange" value="upcoming" />
                </div>
            </form>
        </td>
        <td valign="middle" style="padding-left:5px;">
            <form action="http://dbis-group.uni-muenster.de/dbms/templates/conferences/conferences.php" method="get">
                <div>
                    <input type="submit" name="button_all_previous" value="Previous Confs" />
                    <input type="hidden" name="searchTerm" value="" />
                    <input type="hidden" name="sortBy" value="fromdate" />
                    <input type="hidden" name="sortDirection" value="DESC" />
                    <input type="hidden" name="dateRange" value="previous" />
                </div>
            </form>
        </td>
    </tr>

</table>

<h2>Emphasis classes</h2>
<p class="text-muted">Fusce dapibus, tellus ac cursus commodo, tortor mauris nibh.</p>
<p class="text-primary">Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
<p class="text-secondary">Pellentesque ornare sem lacinia quam venenatis vestibulum.</p>
<p class="text-warning">Etiam porta sem malesuada magna mollis euismod.</p>
<p class="text-danger">Donec ullamcorper nulla non metus auctor fringilla.</p>
<p class="text-success">Duis mollis, est non commodo luctus, nisi erat porttitor ligula.</p>
<p class="text-info">Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
<!--form action="requeset.jsp">
    姓名<input type="text" name="UserName">
    <input type="submit" value="提交">
</form><br>
<form action="Login_session.jsp">
    姓名<input type="text" name="UserName"/>
    <input type="submit" value="提交"/>
</form-->

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01"
            aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Features</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Pricing</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">About</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search">
            <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
<form role="form">
    <div class="form-group">
        <label for="exampleInputEmail1">Email address</label>
        <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
    </div>
    <div class="checkbox">
        <label>
            <input type="checkbox"> Check me out
        </label>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</form>
</body>

</html>
