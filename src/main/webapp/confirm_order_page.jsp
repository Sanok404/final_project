<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 22.10.2022
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<header>
    <%@include file="WEB-INF/include/nav_bar.jspf"%>
</header>
<div class="row">
    <div class="col"></div>
    <div class="col">
        <div class="row">
            <div class="col align-content-center justify-content-center">
                <img src="${pageContext.request.contextPath}${sessionScope.currentCarToShow.imageUrl}"
                     alt="car image">
            </div>
            <div class="col"></div>
        </div>
        <div class="card-body">
            <h5 class="card-title">${sessionScope.currentCarToShow.brand} ${sessionScope.currentCarToShow.model}</h5>
        </div>
        <div class="row">
                <div class="container">
                    <h2><fmt:message key="order.details"/></h2>
                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <td><fmt:message key="client"/>:</td>
                            <td>${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="passport.info"/>:</td>
                            <td>${sessionScope.passport}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="driver.service"/>:</td>
                            <td>
                                <c:if test="${sessionScope.withADriver=='Yes'}">
                                    <fmt:message key="yes"/>
                                </c:if>
                                <c:if test="${sessionScope.withADriver=='No'}">
                                    <fmt:message key="no"/>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td><fmt:message key="from"/>:</td>
                            <td>${sessionScope.dateStart}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="to"/>:</td>
                            <td>${sessionScope.dateEnd}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="full.days"/>:</td>
                            <td>${sessionScope.days}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="cost"/>:</td>
                            <td>${sessionScope.cost}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <form action="controller?command=addNewOrder" method="post">
                    <button class="btn btn-primary" type="submit"><fmt:message key="confirm.order"/></button>
                </form>
        </div>
    </div>
    <div class="col"></div>
</div>
</body>
</html>
