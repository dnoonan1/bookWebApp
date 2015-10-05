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
        <title>BookWebApp | Statistics</title>
        <!-- Bootstrap 3.3.5 -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Statistics</h1>
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
                    <span class="glyphicon glyphicon-trash" title=""></span>
                </button>
                <button form="action-stats">
                    <span class="glyphicon glyphicon-stats" title="Statistics"></span>
                </button>
            </div>
            
            <form id="refresh" action="author?action=stats" method="POST"></form>
            <form id="action-list" action="author?action=list" method="POST"></form>
            <form id="action-add" action="author?action=add" method="POST"></form>
            <form id="action-stats" action="author?action=stats" method="POST"></form>
            <form id="action-delete-selected" action="" method="POST"></form>
            
            <table>
                <tr>
                    <th>Stat</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td class="text-left">Author Count</td>
                    <td class="text-right">${sessionScope.stats.authorCount}</td>
                </tr>
                <tr>
                    <td class="text-left">Authors Added</td>
                    <td class="text-right">${sessionScope.stats.authorsAdded}</td>
                </tr>
                <tr>
                    <td class="text-left">Edits Made</td>
                    <td class="text-right">${sessionScope.stats.editCount}</td>
                </tr>
                <tr>
                    <td class="text-left">Authors Deleted</td>
                    <td class="text-right">${sessionScope.stats.authorsDeleted}</td>
                </tr>
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
        // Add click event to .selectAll to check/uncheck all checkboxes
        $(function() {
            $('.selectAll').click(function() {
                $('input[type="checkbox"]').prop('checked', $(this).prop('checked'));
            });
        });
    </script>
    </body>
</html>
