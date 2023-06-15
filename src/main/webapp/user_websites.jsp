<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Website List</title>
</head>
<body>
<h1>Website List</h1>
<a href="/websites/add?userId=${userId}">Add Website</a>
<form id="delete-form" method="post" action="<c:url value='/websites/del'/>">
    <input type="hidden" name="userId" value="${userId}"/>
    <input type="text" name="id" size="0px"/>
    <button type="submit">Delete</button>
</form>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Created By</th>
        <th>Created At</th>
        <th>Redirect URL</th>
        <th>Private Key</th>
        <th>Is Active</th>
        <th>Updated At</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="website" items="${websites}">
        <tr>
            <form method="post" action="<c:url value='/websites'/>">
                <input type="hidden" name="userId" value="${userId}"/>
                <input type="hidden" name="id" value="${website.id}"/>
                <td>${website.id}</td>
                <td><input type="text" name="displayName" value="${website.displayName}"/></td>
                <td>${website.createdById}</td>
                <td>${website.createdAt}</td>
                <td><input type="text" name="redirectUrl" value="${website.redirectUrl}"/></td>
                <td>${website.privateKey}</td>
                <td>${website.isActive}</td>
                <td>${website.updatedAt}</td>
                <td>
                    <button type="submit">Save</button>
                </td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>