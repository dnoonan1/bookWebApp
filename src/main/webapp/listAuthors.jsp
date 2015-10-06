<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-add">
                    <span class="glyphicon glyphicon-plus" title="Add Author"></span>
                </button>
                <button form="action-delete-selected">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>
            
            <form id="refresh" action="author?action=list" method="POST"></form>
            <form id="action-add" action="author?action=add" method="POST"></form>
            <form id="action-delete-selected" action="author?action=delete" method="POST"></form>
            <form id="action-stats" action="author?action=stats" method="POST"></form>
            
            <table class="list">
                <thead>
                    <tr>
                        <th class="check">
                            <input type="checkbox" class="select-all" title="Select/Deselect All">
                        </th>
                        <th>ID</th>
                        <th class="text-left">Name</th>
                        <th>Date Added</th>
                        <th>Edit / Delete</th>
                    </tr>
                </thead>
                <tbody>
                <c:if test="${authors.size() == 0}">
                    <tr>
                        <td class="text-danger bg-danger" colspan="5">There are no authors currently!</td>
                    </tr>
                </c:if>
                <c:forEach var="a" items="${authors}">
                    <form id="edit-${a.id}" action="author?action=edit&id=${a.id}" method="POST"></form>
                    <form id="delete-${a.id}" action="author?action=delete&id=${a.id}" method="POST"></form>
                    <tr>
                        <td class="check">
                            <input form="action-delete-selected" type="checkbox" name="id" value="${a.id}">
                        </td>
                        <td><fmt:formatNumber value="${a.id}" pattern="0000"></fmt:formatNumber></td>
                        <td class="text-left">${a.name}</td>
                        <td>
                            <fmt:formatDate pattern="MM/dd/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                        </td>
                        <td>
                            <button form="edit-${a.id}">
                                <span class="glyphicon glyphicon-edit" title="Edit"></span>
                            </button>
                            <button form="delete-${a.id}">
                                <span class="glyphicon glyphicon-trash" title="Delete"></span>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th>
                            <input type="checkbox" class="select-all" title="Select/Deselect All">
                        </th>
                        <th>ID</th>
                        <th class="text-left">Name</th>
                        <th>Date Added</th>
                        <th>Edit / Delete</th>
                    </tr>
                </tfoot>
            </table>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-add">
                    <span class="glyphicon glyphicon-plus" title="Add Author"></span>
                </button>
                <button form="action-delete-selected">
                    <span class="glyphicon glyphicon-trash" title="Delete Selected"></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>           
            <c:if test="${errMsg != null}">
                <p class="bg-danger">Sorry, data could not be retrieved:<br>
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
    </body>
</html>
