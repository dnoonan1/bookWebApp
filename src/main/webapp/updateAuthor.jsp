<%-- 
    Document   : addAuthor
    Created on : Sep 27, 2015, 6:21:45 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BookWebApp | Edit Author</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <header>
            <h1>Edit Author</h1>
        </header>
        <form action="author?action=edit" method="POST">
            <label for="id">ID: ${author.id}</label>
            <input type="hidden" id="id" name="id" value="${author.id}">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" value="${author.name}" required>
            <label for="dateAdded">Date Added</label>
            <input type="text" id="dateAdded" name="dateAdded" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${author.dateAdded}"></fmt:formatDate>" placeholder="mm/dd/yyyy" required>
            <input type="submit" value="Submit">
        </form>
    </body>
    <c:if test="${errMsg != null}">
        <p class="error">Sorry, data could not be retrieved:<br>
           ${errMsg}</p>
    </c:if>
    <c:if test="${updated != null}">
        <p class="update">Author updated!</p>
        <a href="author?action=list">View All Authors</a>
    </c:if>
</html>

