<%-- 
    Document   : index
    Created on : Oct 26, 2015, 2:05:21 PM
    Author     : dnoonan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>BookWebApp | Home</title>
        <jsp:include page="WEB-INF/jspf/styles.jsp" />
    </head>
    
    <body>
        <div class="container">
            
            <header>
                <jsp:include page="WEB-INF/jspf/header.jsp" />
            </header>
            
            <h1>Pick an Administrative Task</h1>
            <ul>
                <li><a href="author?action=list">View All Authors</a></li>
                <li><a href="book?action=list">View All Books</a></li>
            </ul>
            
        </div>
    </body>
</html>
