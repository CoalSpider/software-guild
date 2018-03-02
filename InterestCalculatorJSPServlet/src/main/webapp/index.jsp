<%-- 
    Document   : Index
    Created on : Feb 27, 2018, 1:51:56 PM
    Author     : Ben Norman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Interest Calculator</title>
    </head>
    <body>
        <h1>Interest Calculator</h1>
        <form method ="post" action="IntrestCalculatorServlet">
            Annual Intrest Rate<input type="text" name="intrest"/><br/>
            Principle<input type="text" name="principle"/><br/>
            Years<input type="text" name="years"/><br/>
            <button type="submit">CALCULATE</button>
        </form>
    </body>
</html>
