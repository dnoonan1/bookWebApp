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
        <jsp:include page="WEB-INF/jspf/styles.jsp" />
    </head>
    <body>
        <div class="container">
            
            <header>
                <h1>Statistics</h1>
            </header>
            
            <form id="refresh" action="author?action=viewStats" method="POST"></form>
            <form id="action-list" action="author?action=list" method="POST"></form>
            
            <div class="controls">
                <button form="refresh">
                    <span class="glyphicon glyphicon-refresh" title="Refresh"></span>
                </button>
                <button form="action-list">
                    <span class="glyphicon glyphicon-list-alt" title="List Authors"></span>
                </button>
            </div>
            
            <table>
                <tr>
                    <th class="text-left">Stat</th>
                    <th class="text-right">Value</th>
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
                <p class="bg-danger text-danger">Sorry, data could not be retrieved:<br>
                    ${errMsg}
                </p>
            </c:if>
                
        </div>
    </body>
</html>
