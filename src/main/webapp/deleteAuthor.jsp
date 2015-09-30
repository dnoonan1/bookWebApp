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
        <title>BookWebApp | Delete Author</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <header>
            <h1>Delete Author</h1>
        </header>
        <form action="author?action=delete" method="POST">
            <label for="authorId">ID</label>
            <input type="number" name="authorId" id="authorId" value="${param.id}" autofocus required>
            <input type="submit" value="Submit">
        </form>
    </body>
    <c:if test="${errMsg != null}">
        <p class="error">Sorry, data could not be retrieved:<br>
           ${errMsg}</p>
    </c:if>
    <c:if test="${authorId != null}">
        <p class="update">Author deleted!</p>
        <a href="author?action=list">View All Authors</a>
    </c:if>
</html>

