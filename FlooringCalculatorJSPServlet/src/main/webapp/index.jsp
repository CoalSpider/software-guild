<%-- 
    Document   : index
    Created on : Feb 27, 2018, 12:39:08 PM
    Author     : Ben Norman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Flooring Calculator</title>
    </head>
    <body>
        <h1>Flooring Calculator</h1>
        <form method ="post" action="FlooringCalculatorServlet">
            Width In Ft<input type="text" name="width"/><br/>
            Length In Ft<input type="text" name="length"/><br/>
            Cost Per Ft^2<input type="text" name="cost per ft^2"/><br/>
            <button type="submit">CALCULATE</button>
        </form>
    </body>
</html>
