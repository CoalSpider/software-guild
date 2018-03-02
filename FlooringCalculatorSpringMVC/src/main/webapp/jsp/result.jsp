<%-- 
    Document   : result
    Created on : Feb 28, 2018, 2:09:03 PM
    Author     : Ben Norman
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Calculations</title>
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
