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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BookWebApp | Author List (RESTful HATEOAS Version)</title>
        <jsp:include page="WEB-INF/jspf/styles.jsp" />
    </head>
    <body>
        <div class="container">
            
            <header>
                <jsp:include page="WEB-INF/jspf/header.jsp" />
                <h1>Author List (RESTful HATEOAS Version)</h1>
            </header>
            
            <form id="refresh"></form>
            <form id="action-add" action="author?action=add" method="POST"></form>
            <form id="action-delete-selected" action="author?action=delete" method="POST"></form>
            <form id="action-stats" action="author?action=viewStats" method="POST"></form>
            
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
            
            <table class="list authorList">
                <thead>
                    <tr>
                        <th class="check">
                            <input type="checkbox" class="select-all" title="Select/Deselect All">
                        </th>
                        <!-- <th>ID</th> HATEOAS doesn't send ID -->
                        <th class="text-left">Name</th>
                        <th>Date Added</th>
                        <th>Edit / Delete</th>
                    </tr>
                </thead>
                <tbody>
                
                </tbody>
                <tfoot>
                    <tr>
                        <th>
                            <input type="checkbox" class="select-all" title="Select/Deselect All">
                        </th>
                        <!-- <th>ID</th> HATEOAS doesn't send ID -->
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
                <p class="bg-danger text-danger">Sorry, data could not be retrieved:<br>
                    ${errMsg}
                </p>
            </c:if>
        </div>
        
    <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="resources/js/authorListRest.js"></script>
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
