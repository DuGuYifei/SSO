<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add User</title>
</head>
<body>
<h1>Add User</h1>
<form method="post" action="/users/add">
    <label for="username">Username:</label>
    <input type="text" name="username" id="username"/><br/>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password"/><br/>
    <label for="email">Email:</label>
    <input type="email" name="email" id="email"/><br/>
    <button type="submit">Save</button>
</form>
</body>
</html>