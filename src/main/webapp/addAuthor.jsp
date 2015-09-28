<%-- 
    Document   : addAuthor
    Created on : Sep 27, 2015, 6:21:45 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BookWebApp | Add Author</title>
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
        <h2>Add Author</h2>
        <form action="author?action=add" method="POST">
            <label for="authorName">Name</label>
            <input type="text" name="authorName" id="authorName" value="${param.authorName}" autofocus required>
            <input type="submit" value="Submit">
        </form>
    </body>
    <c:if test="${errMsg != null}">
        <p class="error">Sorry, data could not be retrieved:<br>
           ${errMsg}</p>
    </c:if>
    <c:if test="${author != null}">
        <p class="update">Author added!</p>
        <a href="author?action=list">View All Authors</a>
    </c:if>
</html>

