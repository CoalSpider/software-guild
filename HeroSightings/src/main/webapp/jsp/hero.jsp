<%-- 
    Document   : hero
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
        <link href="${pageContext.request.contextPath}/css/debugStyles.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1>Spring MVC Application from Archetype</h1>
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
                    <h4>Image of Hero</h4>
                </div>
                <div class="col-sm-3">
                    <h4><c:out value="${hero.name}"/></h4>
                    <c:out value="${hero.description}"/>
                </div>
                <div class="col-sm-3">
                    <h4>Powers</h4>
                    <c:forEach var="power" items="${hero.powers}">
                        <!--link to the power page-->
                        <a href="${pageContext.request.contextPath}/superpower${power.id}"><c:out value="${power.name}"/></a>
                    </c:forEach>
                </div>
                <div class="col-sm-3">
                    <h4>Organizations</h4>
                    <c:forEach var="org" items="${hero.organizations}">
                        <!--link to the org pag-->
                        <a href="${pageContext.request.contextPath}/orgganization"${org.id}><c:out value="${org.name}}"/></a>
                    </c:forEach>
                </div>
            </div>
            <div class="row">
                <a href="${pageContext.request.contextPath}/editHero${hero.id}">Edit Hero</a>
                <a href="${pageContext.request.contextPath}/deleteHero${hero.id}">Delete Hero</a>
            </div>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
