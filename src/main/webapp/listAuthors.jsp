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
            
            <form id="refresh" action="author?action=list" method="POST"></form>
            <form id="add-author" action="author?action=add" method="POST"></form>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="add-author">
                    <span class="glyphicon glyphicon-plus" title="Add Author"></span>
                </button>
                <button form="delete-selection">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
            </div>
            
            <form id="delete-selection" action="author?action=delete" method="POST">
            <table>
                <tr>
                    <th class="select">
                        <input type="checkbox" class="selectAll" title="Select/Deselect All">
                    </th>
                    <th>ID</th>
                    <th>Name</th>
                    <th class="text-right">Date Added</th>
                    <th class="text-center">Edit / Delete</th>
                </tr>
            <c:forEach var="a" items="${authors}">
                <form id="edit-${a.id}" action="author?action=edit&id=${a.id}" method="POST"></form>
                <form id="delete-${a.id}" action="author?action=delete&id=${a.id}" method="POST"></form>
                <tr>
                    <td class="select">
                        <input type="checkbox" class="checkbox" name="id" value="${a.id}">
                    </td>
                    <td>${a.id}</td>
                    <td>${a.name}</td>
                    <td class="text-right">
                        <fmt:formatDate pattern="MM/dd/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                    </td>
                    <td class="text-center">
                        <button form="edit-${a.id}">
                            <span class="glyphicon glyphicon-edit" title="Edit"></span>
                        </button>
                        <button form="delete-${a.id}">
                            <span class="glyphicon glyphicon-trash" title="Delete"></span>
                        </button>
                    </td>
                </tr>
            </c:forEach>
                <tr>
                    <th class="select">
                        <input type="checkbox" class="selectAll" title="Select/Deselect All">
                    </th>
                    <th>ID</th>
                    <th>Name</th>
                    <th class="text-right">Date Added</th>
                    <th class="text-center">Edit / Delete</th>
                </tr>
            </table>
            </form>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="add-author">
                    <span class="glyphicon glyphicon-plus" title="Add Author"></span>
                </button>
                <button form="delete-selection">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
            </div>
            
            <c:if test="${errMsg != null}">
                <p class="error">Sorry, data could not be retrieved:<br>
                    ${errMsg}
                </p>
            </c:if>
        </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        // Add click event to .selectAll to check/uncheck all checkboxes
        $(function() {
            $('.selectAll').click(function() {
                $('input[type="checkbox"]').prop('checked', $(this).prop('checked'));
            });
        });
    </script>
    </body>
</html>
