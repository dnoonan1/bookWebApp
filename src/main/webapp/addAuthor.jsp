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
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Add Author</h1>
            </header>

            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-list">
                    <span class="glyphicon glyphicon-list-alt" title="List Authors"></span>
                </button>
                <button form="action-add">
                    <span class="glyphicon glyphicon-plus" title="Add Author"></span>
                </button>
                <button form="action-delete-selected">
                    <span class="glyphicon glyphicon-trash" title="Cancel"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>

            <form id="refresh" action="author?action=add" method="POST"></form>
            <form id="action-list" action="author?action=list" method="POST"></form>
            <form id="action-add" action="author?action=add" method="POST"></form>
            <form id="action-stats" action="author?action=stats" method="POST"></form>
            <form id="action-delete-selected" action="author?action=list" method="POST"></form>

            <form action="author?action=add" method="POST">
                <label for="name">Name</label>
                <input type="text" name="name" id="authorName" value="${param.authorName}" autofocus required>
                <input type="submit" value="Submit">
            </form>
        <c:if test="${errMsg != null}">
            <div class="error">Sorry, data could not be retrieved:<br>
                ${errMsg}
            </div>
        </c:if>
        <c:if test="${author != null}">
            <div class="update">
                <fmt:formatDate value="${timestamp}" pattern="yyyy-mm-dd hh:mma"></fmt:formatDate>
                Author added!
            </div>
        </c:if>
        </div>               
    </body>
</html>

