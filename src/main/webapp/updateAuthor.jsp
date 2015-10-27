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
        <title>BookWebApp | Edit Author</title>
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container">
            
            <header>
                <h1>Edit Author</h1>
            </header>
            
            <form id="refresh" action="author?action=edit&id=${author.authorId}" method="POST"></form>
            <form id="action-list" action="author?action=list" method="POST"></form>
            <form id="action-stats" action="author?action=stats" method="POST"></form>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-list">
                    <span class="glyphicon glyphicon-list-alt" title="List Authors"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>
            
            <form id="update-author" action="author?action=edit" method="POST">
                <label for="id">ID: ${author.authorId}</label>
                <input type="hidden" id="id" name="id" value="${author.authorId}">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${author.authorName}" required>
                <label for="dateAdded">Date Added</label>
                <input type="text" id="dateAdded" name="dateAdded" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${author.dateAdded}"></fmt:formatDate>" placeholder="mm/dd/yyyy" required>
                <!-- <input type="submit" value="Submit"> -->
                <c:if test="${not empty author.bookSet}">
                    <select id="books">
                        <c:forEach var="b" items="${author.bookSet}">
                            <option value="${b.title}">${b.title}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <button form="update-author" class="yes" title="Save Changes">
                    <span class="glyphicon glyphicon-ok"></span>
                </button>
                <button form="action-list" class="no" title="Cancel">
                    <span class="glyphicon glyphicon-remove"></span>
                </button>
            </form>
        <c:if test="${errMsg != null}">
            <p class="bg-danger text-danger">Sorry, data could not be retrieved:<br>
                ${errMsg}
            </p>
        </c:if>
        <c:if test="${updated != null}">
            <div class="update">
                <span class="text-success bg-success">Author updated!</span>
                <br>
                <span class="text-info bg-info">
                    Time: <fmt:formatDate value="${timestamp}" pattern="mm-dd-yyy hh:mm:ssa"></fmt:formatDate>
                </span>
            </div>
        </c:if>    
        </div>
    </body>   
</html>

