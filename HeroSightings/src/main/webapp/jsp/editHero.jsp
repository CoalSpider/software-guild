<%-- 
    Document   : edit
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
        <%@ include file="header.jsp" %>  
    </head>
    <body>
        <div class="container">
            <form method="POST" action="${pageContext.request.contextPath}/saveChangesHero">
                <div class="row">
                    <div class="col-sm-3">
                        <h4>Image of Hero</h4>
                    </div>
                    <div class="col-sm-3">
                        <input name="id" type="hidden" value="${hero.id}">
                        <label for="name" value="name">Name</label>
                        <label for="desc" value="description">Description</label>
                        <input name="name" id="name" type="text" value="${hero.name}"/> 
                        <textarea name="description" id="desc" value="${hero.description}" rows="4" cols="35"><c:out value="${hero.description}"/></textarea>

                    </div>
                    <div class="col-sm-3">
                        <h4>Powers</h4>
                        <c:forEach var="power" items="${hero.powers}">
                            <ul>
                                <input name="powersArray" type="checkbox" checked value="${power.id}"/>
                                <c:out value="${power.name}"/>
                            </ul>
                        </c:forEach>
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
                    <button type="submit">Save Changes</button>
                </div>
            </form>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
