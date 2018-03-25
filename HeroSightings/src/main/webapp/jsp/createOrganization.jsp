<%-- 
    Document   : editLocation
    Created on : Mar 20, 2018, 12:08:01 PM
    Author     : Ben Norman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">   
        <link href="${pageContext.request.contextPath}/css/debugStyles.css" rel="stylesheet">     

        <%@ include file="header.jsp" %>
    </head>
    <body>
        <div class="container">
            <form method="POST" action="${pageContext.request.contextPath}/saveNewOrganization">
                <div class="row">
                    <div class="col-sm-4">
                        <h4>IMAGE OF ORGANIZATION</h4>
                    </div>
                    <div class="col-sm-4">
                        <h4>Superpower</h4>
                        <!--name-->
                        <label for="name" value="name">Name</label>
                        <input name="name" id="name" type="text" value=""/> 
                        <!--description-->
                        <label for="desc" value="description">Description</label>
                        <textarea name="description" id="desc" value="" rows="4" cols="35"></textarea>
                    </div>
                    <div class="col-sm-4">
                        <!--address-->
                        <label for="address" value="address">Address</label>
                        <input name="address" id="address" type="text" value=""/>
                        <!--email-->
                        <label for="email" value="email">Email</label>
                        <input name="email" id="email" type="text" value=""/>
                        <!--phone number-->
                        <label for="phoneNumber" value="phoneNumber">Phone Number</label>
                        <input name="phoneNumber" id="phoneNumber" type="text" value=""/>
                    </div>
                </div>
                <div class="row">
                    <button type="submit">Save Organization</button>
                </div>
            </form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

