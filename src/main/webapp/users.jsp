<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
<h1>User List</h1>
<a href="/users/add">Add User</a>
<form id="delete-form" method="post" action="<c:url value='/users/del'/>">
    <input type="text" name="id" size="0px"/>
    <button type="submit">Delete</button>
</form>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Password</th>
        <th>Created At</th>
        <th>Updated At</th>
        <th>Email</th>
        <th>Confirmation Code</th>
        <th>Confirmed At</th>
        <th>Global Permission</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <form method="post" action="<c:url value='/users'/>">
                <input type="hidden" name="id" value="${user.id}"/>
                <td>${user.id}</td>
                <td><input type="text" name="username" value="${user.username}"/></td>
                <td>${user.password}</td>
                <td>${user.createdAt}</td>
                <td>${user.updatedAt}</td>
                <td><input type="text" name="email" value="${user.email}"/></td>
                <td>${user.confirmationCode}</td>
                <td>${user.confirmedAt}</td>
                <td>${user.globalPermission}</td>
                <td>
                    <button type="submit">Save</button>
                    <a href="/websites?userId=${user.id}">View Websites</a>
                </td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>