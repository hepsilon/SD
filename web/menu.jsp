<%--
  Created by IntelliJ IDEA.
  User: hipol
  Date: 12/12/2019
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebClient</title>
</head>
<body>
    <h1>
        Select Option
    </h1>
    <form method="post" action="select_opt2.action">
        <br>
        <select name="opt" size="1">
            <option value=1>Search</option>
            <option value=2>History</option>
            <option value=3>Logout</option>
        </select>
        <br><br>
        <input type="submit">
    </form>
</body>
</html>
