<%-- 
    Document   : machine
    Created on : Mar 2, 2018, 2:41:05 PM
    Author     : Ben Norman
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-grid.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/machine.css">

    </head>
    <body>
        <div class="container">
            <h1>Vending Machine</h1>
            <hr />
            <div class="row">
                <div class="col-9">
                    <div class="row">
                        <c:forEach var="item" items="${items}">
                            <form id="${item.num}" action="selectItem" method="POST">
                                <input type="hidden" name="num" value="${item.num}"/>
                            </form>
                            <div class="col item" onclick="document.getElementById(${item.num}).submit()">
                                <div class="row itemPart itemNum">
                                    <c:out value="${item.num}"/>
                                </div>
                                <div class="row itemPart">
                                    <c:out value="${item.name}"/>
                                </div>
                                <div class="row itemPart">
                                    <c:out value="$${item.price}"/>
                                </div>
                                <div class="row itemPart itemQuantity">
                                    <c:out value="Quantity Left: ${item.quantity}"/>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="col-3">
                    <div class="pannel">
                        <h3>Total $ In</h3>
                        <div class="row">
                            <input type="text" placeholder="0.00" value="${machineAmount}" readonly />
                        </div>
                        <form action="addMoney" method="POST">
                            <div class="row">
                                <div class="col changeButton">
                                    <button type="submit" formaction="addDollar">Add Dollar</button>
                                </div>
                                <div class="col changeButton">
                                    <button type="submit" formaction="addQuater">Add Quarter</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col changeButton">
                                    <button type="submit" formaction="addDime">Add Dime</button>
                                </div>
                                <div class="col changeButton">
                                    <button type="submit" formaction="addNickel">Add Nickel</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <hr />
                    <div class="pannel">
                        <h3>Messages</h3>
                        <form action="makePurchase" method="POST">
                            <div class="row">
                                <input type="text" value="${purchaseMsg}"readonly>
                            </div>
                            <div class="row">
                                <div class="col-4">
                                    <label>Item:</label>
                                </div>
                                <div class="col-8">
                                    <input type="text" value="${itemChoice}" readonly/>
                                </div>
                            </div>
                            <div class="row">
                                <button type="submit">Make Purchase</button>
                            </div>
                        </form>
                    </div>
                    <hr />
                    <div class="pannel">
                        <h3>Change</h3>
                        <form action="returnChange" method="POST">
                            <div class="row">
                                <input type="text" value="${changeMsg}" readonly>
                            </div>
                            <div class="row">
                                <button type="submit">Change Return</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- jquery -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <!--<script src="js/home.js"></script>-->
    </body>
</html>
