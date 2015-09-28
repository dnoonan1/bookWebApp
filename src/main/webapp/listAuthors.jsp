<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BookWebApp | Author List</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <header>
            <h1>Administration</h1>
        </header>
        <nav>
            <ul>
                <li><a href="index.html">Home</a></li>
                <li><a href="author?action=list">View Authors</a></li>
                <li><a href="author?action=add">Add Author</a></li>
                <li><a href="author?action=update">Update Author</a></li>
                <li><a href="author?action=delete">Delete Author</a></li>
            </ul>
        </nav>
        <h2>Author List</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Author Name</th>
                <th class="text-right">Date Added</th>
            </tr>
        <c:forEach var="a" items="${authors}" varStatus="rowCount">
            <td>${a.authorId}</td>
            <td>${a.authorName}</td>
            <td class="text-right">
                <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
            </td>
        </tr>
        </c:forEach>
        </table>
        <c:if test="${errMsg != null}">
            <p class="error">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>
