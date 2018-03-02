<%-- 
    Document   : result
    Created on : Feb 27, 2018, 12:42:03 PM
    Author     : Ben Norman
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RESULTS</title>
    </head>
    <body>
        <h1>Bill</h1>
        <p>
            Area = <c:out value="${area}"/> ft^2
        </p>
        <p>
            Materials = $<c:out value="${materialCost}"/>
        </p>
        <p>
            Labor = $<c:out value="${laborCost}"/>
        </p>
        <p>
            Total = $<c:out value="${total}"/>
        </p>
        <p>
            <a href="index.jsp">Go Back</a>
        </p>
    </body>
</html>
