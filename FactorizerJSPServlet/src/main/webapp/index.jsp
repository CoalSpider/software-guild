<%-- 
    Document   : index
    Created on : Feb 26, 2018, 8:42:13 PM
    Author     : Ben Norman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Factorizer</title>
    </head>
    <body>
        <h1>Factorizer</h1>
        <p>
            Please enter the number you want to factor:
        </p>
        <form method ="post" action="FactorizerServlet">
            <input type="text" name="numberToFactor"/><br/>
            <!--  <input type="submit" value="Find Factors!!!"/>-->
            <button>Find Factors!!!</button>
        </form>
    </body>
</html>
