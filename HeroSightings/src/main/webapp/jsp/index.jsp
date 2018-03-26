<%-- 
    Document   : index
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
    </head>
    <body>
        <div class="container">
            <h2>Hero Sightings</h2>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/heros">Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superpowers">Superpowers</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                </ul>    
            </div>
            <div class="row">
                <div class="col-sm-5">
                    <h3>Recent Sightings</h3>
                    <c:forEach var="sighting" items="${recentSightings}">
                        <form id="${sighting.id}" method="GET" action="${pageContext.request.contextPath}/sighting${sighting.id}">
                            <div class="borderDiv recentSighting" onclick="document.getElementById(${sighting.id}).submit()">
                            <!--<p><c:out value="${sighting.id}"/></p>-->
                                <p><c:out value="${sighting.location.name}"/></p>
                                <p><c:out value="${sighting.dateAndTime}"/></p>
                                <p><c:out value="${sighting.location.description}"/></p>
                                <!--hidden divs for placing google map pins-->
                                <div class="latitude" style="display: none;"><c:out value="${sighting.location.latitude}"/></div>
                                <div class="longitude" style="display: none;"><c:out value="${sighting.location.longitude}"/></div>
                            </div>
                        </form>
                    </c:forEach>
                </div>
                <div class="col-sm-5">
                    <h3>Google Map</h3>
                    <div id="map-canvas" style="height:500px; width:500px">
                        <script>
                            // code from https://stackoverflow.com/questions/38148097/google-maps-api-without-key/38809129#38809129
                            // hack Google Maps to bypass API v3 key (needed since 22 June 2016 http://googlegeodevelopers.blogspot.com.es/2016/06/building-for-scale-updates-to-google.html)
                            var target = document.head;
                            var observer = new MutationObserver(function (mutations) {
                                for (var i = 0; mutations[i]; ++i) { // notify when script to hack is added in HTML head
                                    if (mutations[i].addedNodes[0].nodeName == "SCRIPT" && mutations[i].addedNodes[0].src.match(/\/AuthenticationService.Authenticate?/g)) {
                                        var str = mutations[i].addedNodes[0].src.match(/[?&]callback=.*[&$]/g);
                                        if (str) {
                                            if (str[0][str[0].length - 1] == '&') {
                                                str = str[0].substring(10, str[0].length - 1);
                                            } else {
                                                str = str[0].substring(10);
                                            }
                                            var split = str.split(".");
                                            var object = split[0];
                                            var method = split[1];
                                            window[object][method] = null; // remove censorship message function _xdc_._jmzdv6 (AJAX callback name "_jmzdv6" differs depending on URL)
                                            //window[object] = {}; // when we removed the complete object _xdc_, Google Maps tiles did not load when we moved the map with the mouse (no problem with OpenStreetMap)
                                        }
                                        observer.disconnect();
                                    }
                                }
                            }); // end of hack / barrowed code
                            // create the map
                            var config = {attributes: true, childList: true, characterData: true};
                            observer.observe(target, config);
                            function myMap() {
                                // set the map options
                                var mapOptions = {
                                    zoom: 8,
                                    center: new google.maps.LatLng(-34.397, 150.644)
                                };
                                // create the map
                                var map = new google.maps.Map(document.getElementById('map-canvas'),
                                        mapOptions);
                                // get hidden latitudes and longitudes
                                var lats = document.getElementsByClassName("latitude");
                                var lngs = document.getElementsByClassName("longitude");
                                // loop through all elements and create a marker
                                // TODO: replace with cluster markers to handle when we have thousands of markers
                                for (var i = 0; i < lats.length; i++) {
                                    var latlang = {lat: Number(lats[i].innerHTML), lng: Number(lngs[i].innerHTML)};
                                    var marker = new google.maps.Marker({
                                        position: latlang,
                                        map: map,
                                        title: "Sighting "+i+"!"
                                    });
                                }
                            }
                        </script>
                        <script src="https://maps.googleapis.com/maps/api/js?callback=myMap"></script>
                    </div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>