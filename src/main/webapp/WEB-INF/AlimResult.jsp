<%--
  Created by IntelliJ IDEA.
  User: mf839-005
  Date: 2016. 8. 23.
  Time: 오전 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Alim Result</title>
</head>
<body bgcolor="yellow">

<table bordercolor="black">
    <tr bgcolor="#87cefa">
        <th>Result</th><th></th>
    </tr>
    <tr>
    <tr bgcolor="yellow"><td>Pre-ordered Users</td><td>${totalPreorder}</td>
    </tr>
    <tr bgcolor="yellow"><td>Withdraws</td><td>${withdraw}</td></tr>
    <tr bgcolor="yellow"><td>Unidentified PhoneNums</td><td>${unidentified}</td></tr>
    <tr bgcolor="#ffc0cb"><td>Overall # of Removed</td><td>${removed}</td></tr>
    <tr bgcolor="#7cfc00"><td>Success</td><td>${success}</td></tr>
</table>
</body>
</html>

