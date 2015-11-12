<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
            <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
                <div id="user">
                    <span class="glyphicon glyphicon-user"></span>
                    <sec:authentication property="principal.username"></sec:authentication>
                    | <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Logout</a>
                </div>
            </sec:authorize>
