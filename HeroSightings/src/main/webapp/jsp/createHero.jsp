<%-- 
    Document   : createHero
    Created on : Mar 20, 2018, 2:10:50 PM
    Author     : Ben Norman
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">   
        <link href="${pageContext.request.contextPath}/css/mainStyles.css" rel="stylesheet">
        <title>JSP Page</title>
        <%@ include file="header.jsp" %>  
    </head>
    <body>
        <div class="container">
            <h2>Hero Sightings</h2>
            <sf:form method="POST" action="${pageContext.request.contextPath}/saveNewHero" modelAttribute="hero">
                <div class="row">
                    <div class="col-sm-3">
                        <h4>Image of Hero</h4>
                    </div>
                    <div class="col-sm-3">
                        <!--name-->
                        <label for="name" value="name">Name</label>
                        <sf:input path="name" name="name" id="name" placeholder="enter name" type="text"/> 
                        <sf:errors path="name" cssclass="error"></sf:errors>
                            <!--description-->
                            <label for="desc" value="description">Description</label>
                        <sf:textarea path="description" name="description" id="desc" placeholder="enter description" rows="4" cols="35"></sf:textarea>
                        <sf:errors path="description" cssclass="error"></sf:errors>
                        </div>
                        <div class="col-sm-3">
                            <h4>Powers</h4>
                            <!--checked boxes -->
                        <c:forEach var="power" items="${hero.powers}">
                            <ul>
                                <input name="powersArray" type="checkbox" checked value="${power.id}"/>
                                <c:out value="${power.name}"/>
                            </ul>
                        </c:forEach>
                        <!--unchecked boxes-->
                        <c:forEach var="power" items="${powers}">
                            <ul>
                                <input name="powersArray" type="checkbox"value="${power.id}"/>
                                <c:out value="${power.name}"/>
                            </ul>
                        </c:forEach>
                    </div>
                    <div class="col-sm-3">
                        <h4>Organizations</h4>
                        <c:forEach var="org" items="${hero.organizations}">
                            <ul>
                                <input name="organizationArray" type="checkbox" checked value="${org.id}"/>
                                <c:out value="${org.name}"/>
                            </ul>
                        </c:forEach>
                        <c:forEach var="org" items="${organizations}">
                            <ul>
                                <input name="organizationArray" type="checkbox" value="${org.id}"/>
                                <c:out value="${org.name}"/>
                            </ul>
                        </c:forEach>
                    </div>
                </div>
                <div class="row">
                    <sf:button type="submit">Create Hero</sf:button>
                </div>
            </sf:form>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
