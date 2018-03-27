<%-- 
    Document   : editLocation
    Created on : Mar 20, 2018, 12:08:01 PM
    Author     : Ben Norman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">    
        <link href="${pageContext.request.contextPath}/css/mainStyles.css" rel="stylesheet">

        <%@ include file="header.jsp" %>
    </head>
    <body>
        <div class="container">
            <h2>Hero Sightings</h2>
            <sf:form method="POST" action="${pageContext.request.contextPath}/saveChangesLocation" modelattribute="location">
                <div class="row">
                    <div class="col-sm-4">
                        <h4>IMAGE OF LOCATION</h4>
                    </div>
                    <div class="col-sm-4">
                        <h4>Location</h4>
                        <!--name-->
                        <label for="name" value="name">Name</label>
                        <sf:input path="name" placeholder="enter name" name="name" id="name" type="text" value="${location.name}"/> 
                        <sf:errors path="name" cssclass="error"></sf:errors>
                        <!--description-->
                        <label for="desc" value="description">Description</label>
                        <sf:textarea path="description" placeholder="enter description" name="description" id="desc" rows="4" cols="35" value="${location.description}"></sf:textarea>
                        <sf:errors path="description" cssclass="error"></sf:errors>
                    </div>
                    <div class="col-sm-4">
                        <!--address-->
                        <label for="address" value="address">Address</label>
                        <sf:input path="address" placeholder="enter address" name="address" id="address" type="text" value="${location.address}"/>
                        <sf:errors path="address" cssclass="error"></sf:errors>
                        <!--latitude-->
                        <label for="latitude" value="latitude">Latitude</label>
                        <sf:input path="latitude" placeholder="enter latitude" name="latitude" id="latitude" type="number" step="0.00001" value="${location.latitude}"/>
                        <sf:errors path="latitude" cssclass="error"></sf:errors>
                        <!--longitude-->
                        <label for="longitude" value="longitude">Longitude</label>
                        <sf:input path="longitude" placeholder="enter longitude" name="longitude" id="longitude" type="number" step="0.00001" value="${location.longitude}"/>
                        <sf:errors path="longitude" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="row">
                    <sf:button type="submit">Save Changes</sf:button>
                </div>
            </sf:form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

