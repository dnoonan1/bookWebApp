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
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Author List</h1>
            </header>
            
            <div class="controls">
                <a href="author?action=list">
                    <button>
                        <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                    </button>
                </a>
                <a href="author?action=add">
                    <button>
                        <span class="glyphicon glyphicon-plus" title="Add Author"></span>
                    </button>
                </a>
                <button type="submit" form="selection">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
            </div>
            
            <form id="selection" action="author?action=delete" method="POST">
            <table>
                <tr>
                    <th class="select">
                        <input type="checkbox" id="selectAll" title="Select/Deselect All">
                    </th>
                    <th>ID</th>
                    <th>Name</th>
                    <th class="text-right">Date Added</th>
                    <th class="text-center">Edit / Delete</th>
                </tr>
            <c:forEach var="a" items="${authors}">
                <td class="select">
                    <input type="checkbox" class="checkbox" name="id" value="${a.id}">
                </td>
                <td>${a.id}</td>
                <td>${a.name}</td>
                <td class="text-right">
                    <fmt:formatDate pattern="MM/dd/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                </td>
                <td class="text-center">
                    <a href="author?action=edit&id=${a.id}">
                        <span class="glyphicon glyphicon-edit" title="Edit"></span>
                    </a>
                    &nbsp;
                    <a href="author?action=delete&id=${a.id}">
                        <span class="glyphicon glyphicon-trash" title="Delete"></span>
                    </a>
                </td>
            </tr>
            </c:forEach>
            </table>
            </form>
            <c:if test="${errMsg != null}">
                <p class="error">Sorry, data could not be retrieved:<br>
                    ${errMsg}
                </p>
            </c:if>
        </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        $(function() {
            $('#selectAll').click(function() {
                $('.checkbox').prop("checked", $('#selectAll').prop("checked"));
            });
        });
    </script>
    </body>
</html>
