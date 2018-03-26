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
        <link href="${pageContext.request.contextPath}/css/mainStyles.css" rel="stylesheet"> 

        <%@ include file="header.jsp" %>
    </head>
    <body>
        <div class="container">
            <h2>Hero Sightings</h2>
            <form method="POST" action="${pageContext.request.contextPath}/saveChangesSighting">
                <div class="row">
                    <div class="col-sm-4">
                        <h4>IMAGE OF SIGHTING</h4>
                    </div>
                    <div class="col-sm-4">
                        <h4>Sighting</h4>
                        <!--hidden id-->
                        <input name="id" type="hidden" value="${sighting.id}">
                        <!--location dropdown-->
                        <select name="locationId">
                            <option selected="selected" value="${sighting.location.id}"><c:out value="${sighting.location.name}"/></option>
                            <c:forEach var="location" items="${locations}">
                                <option value="${location.id}"><c:out value="${location.name}"/></option>
                            </c:forEach>
                        </select>
                        <!--dateAndTime-->
                        <label for="date" value="date">Date</label>
                        <input name="date" id="date" type="date" max="9999-01-01" value="${date}"/> 
                        <label for="time" value="time">Time</label>
                        <input name="time" id="time" type="time" max="9999-01-01" value="${time}"/> 
                    </div>
                    <div class="col-sm-4">
                        <h4>Heros</h4>
                        <!--display check boxes of heros to select-->
                        <c:forEach var="hero" items="${sightingHeros}">
                            <ul>
                                <input name="herosArray" type="checkbox" checked value="${hero.id}"/>
                                <c:out value="${hero.name}"/>
                            </ul>
                        </c:forEach>
                        <c:forEach var="hero" items="${heros}">
                            <ul>
                                <input name="herosArray" type="checkbox"value="${hero.id}"/>
                                <c:out value="${hero.name}"/>
                            </ul>
                        </c:forEach>
                    </div>
                </div>
                <div class="row">
                    <button type="submit">Save Changes</button>
                </div>
            </form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

