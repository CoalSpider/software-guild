<%-- 
    Document   : result
    Created on : Feb 27, 2018, 1:52:03 PM
    Author     : Ben Norman
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Results</title>
    </head>
    <body>
        <h1>Results</h1>
        <div>
            <c:forEach var="currentYear" items="${accountYears}">
                Year: <c:out value="${currentYear.year}"/></br>
                Start: $<c:out value="${currentYear.startPrinciple}"/></br>
                Interest Earned: $<c:out value="${currentYear.intrestEarned}"/></br>
                End: $<c:out value="${currentYear.endPrinciple}"/></br></br>
            </c:forEach>
        </div>
        <p>
            <a href="index.jsp">Go Back</a>
        </p>
    </body>
</html>
