<%-- 
    Document   : bookList
    Created on : Oct 26, 2015, 1:02:24 PM
    Author     : dnoonan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>BookWebApp | Book List</title>
        <jsp:include page="WEB-INF/jspf/styles.jsp" />
    </head>
    <body>
        <div class="container">
            
            <header>
                <jsp:include page="WEB-INF/jspf/header.jsp" />
                <h1>Book List</h1>
            </header>
            
            <form id="refresh" action="book?action=list" method="POST"></form>
            <form id="action-add" action="book?action=add" method="POST"></form>
            <form id="action-delete-selected" action="book?action=delete" method="POST"></form>
            <form id="action-stats" action="book?action=viewStats" method="POST"></form>
            
            <a href="../bookWebApp">bookWebApp</a>
            <a href="main">main</a>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-add">
                    <span class="glyphicon glyphicon-plus" title="Add Book"></span>
                </button>
                <button form="action-delete-selected">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>
            
            <table class="list">
                <thead>
                    <tr>
                        <th class="check">
                            <input type="checkbox" class="select-all" title="Select/Deselect All">
                        </th>
                        <th>ID</th>
                        <th class="text-left">Title</th>
                        <th>ISBN</th>
                        <th>Author</th>
                        <th>Edit / Delete</th>
                    </tr>
                </thead>
                <tbody>
                <c:if test="${empty books}">
                    <tr>
                        <td class="text-danger bg-danger" colspan="5">There are no books currently!</td>
                    </tr>
                </c:if>
                <c:forEach var="b" items="${books}">
                    <form id="edit-${b.bookId}" action="book?action=edit&id=${b.bookId}" method="POST"></form>
                    <form id="delete-${b.bookId}" action="book?action=delete&id=${b.bookId}" method="POST"></form>
                    <tr>
                        <td class="check">
                            <input form="action-delete-selected" type="checkbox" name="id" value="${b.bookId}">
                        </td>
                        <td><fmt:formatNumber value="${b.bookId}" pattern="0000"></fmt:formatNumber></td>
                        <td class="text-left">${b.title}</td>
                        <td>${b.isbn}</td>
                        <c:set var="a" value="${b.authorId}"></c:set>
                        <td>
                            <a href="author?action=edit&id=${a.authorId}">
                                ${a.authorName} (${a.authorId})
                            </a>
                        </td>
                        <td>
                            <button form="edit-${b.bookId}">
                                <span class="glyphicon glyphicon-edit" title="Edit"></span>
                            </button>
                            <button form="delete-${b.bookId}">
                                <span class="glyphicon glyphicon-trash" title="Delete"></span>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th class="check">
                            <input type="checkbox" class="select-all" title="Select/Deselect All">
                        </th>
                        <th>ID</th>
                        <th class="text-left">Title</th>
                        <th>ISBN</th>
                        <th>Author</th>
                        <th>Edit / Delete</th>
                    </tr>
                </tfoot>
            </table>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-add">
                    <span class="glyphicon glyphicon-plus" title="Add Book"></span>
                </button>
                <button form="action-delete-selected">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>           
            <c:if test="${errMsg != null}">
                <p class="bg-danger text-danger">Sorry, data could not be retrieved:<br>
                    ${errMsg}
                </p>
            </c:if>
        </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        // Add click event to .select-all to check/uncheck all checkboxes
        $(function() {
            $('.select-all').click(function() {
                $('input[type="checkbox"]').prop('checked', $(this).prop('checked'));
            });
        });
    </script>
</html>
