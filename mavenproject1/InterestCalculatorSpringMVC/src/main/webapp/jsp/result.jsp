<%-- 
    Document   : result
    Created on : Feb 28, 2018, 2:45:54 PM
    Author     : Ben Norman
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
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
