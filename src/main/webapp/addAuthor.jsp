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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BookWebApp | Add Author</title>
        <jsp:include page="WEB-INF/jspf/styles.jsp" />
    </head>
    <body>
        <div class="container">
            
            <header>
                <jsp:include page="WEB-INF/jspf/header.jsp" />
                <h1>Add Author</h1>
            </header>

            <form id="action-list" action="author?action=list" method="POST"></form>
            <form id="action-stats" action="author?action=viewStats" method="POST"></form>
            
            <div class="controls">
                <button form="action-list">
                    <span class="glyphicon glyphicon-list-alt" title="List Authors"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>

            <form action="author?action=add" method="POST">
                <label for="name">Name</label>
                <input type="text" name="name" id="authorName" value="${param.name}" autofocus required>
                <input type="submit" value="Create New" class="btn btn-primary">
            </form>
        <c:if test="${errMsg != null}">
            <p class="bg-danger text-danger">Sorry, data could not be retrieved:<br>
                ${errMsg}
            </p>
        </c:if>
        <c:if test="${author != null}">
            <div class="update">
                <span class="text-success bg-success">Author added!</span>
                <br>
                <span class="text-info bg-info">
                    Time: <fmt:formatDate value="${timestamp}" pattern="mm-dd-yyy hh:mm:ssa"></fmt:formatDate>
                </span>
            </div>
        </c:if>
        </div>               
    </body>
</html>

