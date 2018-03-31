<%-- 
    Document   : location
    Created on : Mar 20, 2018, 2:10:50 PM
    Author     : Ben Norman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">   
        <link href="${pageContext.request.contextPath}/css/mainStyles.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h2>Hero Sightings</h2>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/heros">Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superpowers">Superpowers</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                </ul>    
            </div>
            <div class="row">
                <div class="col-sm-3">
                    <h4>Image of Location</h4>
                </div>
                <div class="col-sm-3">
                    <h4><c:out value="${location.name}"/></h4>
                    <c:out value="${location.description}"/>
                </div>
                <div class="col-sm-3">
                    <c:out value="${location.address}"/>
                    <c:out value="${location.latitude}"/>
                    <c:out value="${location.longitude}"/>
                </div>
                <div class="col-sm-3">
                    <h4>Sightings</h4>
                    MAP PLACEHOLDER
                </div>
            </div>
            <div class="row">
                <a class="btn btn-default createButton" href="${pageContext.request.contextPath}/editLocation${location.id}">Edit Location</a>
                <a class="btn btn-default createButton" href="${pageContext.request.contextPath}/deleteLocation${location.id}" onclick="return confirm('delete location?')">Delete Location</a>
            </div>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
