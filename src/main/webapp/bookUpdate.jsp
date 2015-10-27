<%-- 
    Document   : updateBook
    Created on : Oct 26, 2015, 2:34:09 PM
    Author     : dnoonan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${param.action == 'add'}">
        <c:set var="title" value="Add"></c:set>
    </c:when>
    <c:when test="${param.action == 'edit'}">
        <c:set var="title" value="Edit"></c:set>
        <c:set var="author" value="${book.authorId}"></c:set>
    </c:when>
</c:choose>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>BookWebApp | ${title} Book</title>
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container">
            
            <h1>${title} Book</h1>
            
            <form id="refresh" action="book?action=${param.action}" method="POST"></form>
            <form id="action-list" action="book?action=list" method="POST"></form>
            <form id="action-stats" action="book?action=stats" method="POST"></form>
            
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
            
            <form id="update-form" action="book?action=${param.action}" method="POST">
                <input type="hidden" name="subaction" value="save">
                <table>
                    <c:if test="${param.action == 'edit'}">
                        <tr>
                            <th>ID</th>
                            <td>
                                <input type="text" ID id="id" name="id" value="${book.bookId}" readonly>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>Title</th>
                        <td>
                            <input type="text" id="title" name="title" value="${book.title}">
                        </td>
                    </tr>
                    <tr>
                        <th>ISBN</th>
                        <td>
                            <input type="text" id="isbn" name="isbn" value="${book.isbn}">
                        </td>
                    </tr>
                    <c:set var="a" value="${book.authorId}"></c:set>
                    <tr>
                        <th>Author</th>
                        <td>    
                            <select name="authorId">
                                <c:forEach var="a" items="${authors}">
                                    <option value="${a.authorId}" <c:if test="${a.authorId == author.authorId}"></c:if>>${a.authorName} (${a.authorId})</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
                <button form="update-form" class="yes" title="Save">
                    <span class="glyphicon glyphicon-ok"></span>
                </button>
            </form>
        </div>
    </body>
</html>
