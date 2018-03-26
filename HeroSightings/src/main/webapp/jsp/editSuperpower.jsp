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
            <form method="POST" action="${pageContext.request.contextPath}/saveChangesSuperpower">
                <div class="row">
                    <div class="col-sm-5">
                        <h4>IMAGE OF SUPERPOWER</h4>
                    </div>
                    <div class="col-sm-5">
                        <h4>Superpower</h4>
                        <input name="id" type="hidden" value="${superpower.id}">
                        <!--name-->
                        <label for="name" value="name">Name</label>
                        <input name="name" id="name" type="text" value="${superpower.name}"/> 
                        <!--description-->
                        <label for="desc" value="description">Description</label>
                        <textarea name="description" id="desc" value="${superpower.description}" rows="4" cols="35"><c:out value="${superpower.description}"/></textarea>
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
