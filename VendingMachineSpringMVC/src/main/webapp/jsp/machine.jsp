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
                <div class="col-9" id="itemPannel">
                    <!-- Item rows will be generated on the fly by javascript -->
                    <!-- 3 should be the number of items in a row, no more, no less -->
                    <!-- We will generate a complex statement 
                        <div class="col item">
                            <div class="row itemPart itemNum">1</div>
                            <div class="row itemPart">Snickers</div>
                            <div class="row itemPart">$1.85</div>
                            <div class="row itemPart itemQuantity">Qyantify Left: 9</div>
                        </div> -->
                    <div class="row" id="itemRow">
                        <c:each></c:each>
                    </div>
                </div>

                <div class="col-3" id="inputPannel">
                    <div class="pannel">
                        <h3>Total $ In</h3>
                        <div class="row">
                            <input id="moneyInput" type="text" placeholder="0.00" readonly />
                        </div>
                        <div class="row">
                            <div class="col">
                                <button id="addDollar">Add Dollar</button>
                            </div>
                            <div class="col">
                                <button id="addQuarter">Add Quater</button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <button id="addDime">Add Dime</button>
                            </div>
                            <div class="col">
                                <button id="addNickle">Add Nickle</button>
                            </div>
                        </div>
                    </div>
                    <hr />
                    <div class="pannel">
                        <h3>Messages</h3>
                        <div class="row">
                            <input id="messageInput" type="text" readonly>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                <label id="item">Item:</label>
                            </div>
                            <div class="col-8">
                                <input type="text" id="itemChoice" readonly/>
                            </div>
                        </div>
                        <div class="row">
                            <button id="makePurchase">Make Purchase</button>
                        </div>
                    </div>
                    <hr />
                    <div class="pannel">
                        <h3>Change</h3>
                        <div class="row">
                            <input id="changeInput" type="text" readonly>
                        </div>
                        <div class="row">
                            <button id="changeReturn">Change Return</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- jquery -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <!--<script src="js/home.js"></script>-->
    </body>
</html>
