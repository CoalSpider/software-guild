<%-- 
    Document   : superpowers
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
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/mainStyles.css" rel="stylesheet">
        <title>Heros</title>
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
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superpowers">Superpowers</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                </ul>    
            </div>
            <div class="borderDiv">
                <c:forEach var="superpower" items="${superpowers}">
                    <a href="${pageContext.request.contextPath}/superpower${superpower.id}"><c:out value="${superpower.name}"/></a>
                </c:forEach>
            </div>
            <div>
                <a class="btn btn-default createButton" href="${pageContext.request.contextPath}/createSuperpower">Create Superpower</a>
            </div>

            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        </div>
    </body>
</html>
