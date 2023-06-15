<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Website</title>
</head>
<body>
<h1>Add Website</h1>
<form method="post" action="/websites/add">
    <input type="text" name="userId" id="userId" readonly value="${userId}"><br/><br/>
    <label for="displayName">Display Name:</label>
    <input type="text" name="displayName" id="displayName"/><br/><br/>
    <label for="redirectUrl">Redirect URL:</label>
    <input type="text" name="redirectUrl" id="redirectUrl"/><br/><br/>
    <button type="submit">Add Website</button>
</form>
</body>
</html>