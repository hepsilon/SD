<%--
  Created by IntelliJ IDEA.
  User: hipol
  Date: 12/12/2019
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="goback" method= "post">
    <button type="submit">back</button><br>
</form>
<form action="login" method="post">
    <table style="with: 50%">
        <tr>
            <td>Username</td>
            <td><input type="text" name="username" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <input type="submit" value="Login" /></form>
</body>
</html>
