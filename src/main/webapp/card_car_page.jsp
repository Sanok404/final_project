<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 18.10.2022
  Time: 01:15
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/image_car_card.css" type="text/css">
</head>
<body>
<header>
    <%@include file="WEB-INF/include/nav_bar.jspf"%>
</header>
<div class="row">
    <div class="col">
    </div>
    <div class="col">
        <div class="card">
            <div class="row">
                <div class="col wrap_images">
                    <img src="${pageContext.request.contextPath}${sessionScope.currentCarToShow.imageUrl}"
                         alt="car image">
                </div>
                <div class="col">
                    <div class="container-fluid">
                        <p> </p>
                        <p> </p>
                        <p> </p>
                        <h6>
                            <fmt:message key="transmission"/>: ${sessionScope.currentCarToShow.carTransmission}
                        </h6>
                        <hr>
                        <h6>
                            <fmt:message key="price.per.day"/>: ${sessionScope.currentCarToShow.price}
                        </h6>
                        <hr>
                        <h6>
                            <fmt:message key="car.class"/>: ${sessionScope.currentCarToShow.carClass}
                        </h6>
                        <hr>
                        <h6>
                            <fmt:message key="price.driver.service"/>
                        </h6>
                    </div>
                </div>
            </div>
            <div class="card-body">
                <h5 class="card-title">${sessionScope.currentCarToShow.brand} ${sessionScope.currentCarToShow.model}</h5>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><fmt:message key="info"/>: ${sessionScope.currentCarToShow.infoAboutCar}</li>
            </ul>
            <div class="card-body">
                <c:if test="${sessionScope.currentUser==null}">
                    <a href="login_page.jsp" class="btn btn-primary"
                       role="button"
                       aria-pressed="true"><fmt:message key="login.to.order"/></a>
                </c:if>
                <c:if test="${sessionScope.currentUser!=null}">
                    <a href="create_order.jsp" class="btn btn-primary"
                       role="button"
                       aria-pressed="true"><fmt:message key="order"/> </a>
                </c:if>
                <c:if test="${sessionScope.currentUser.role=='ADMIN'}">
                    <a href="edit_car.jsp" class="btn btn-outline-secondary"><fmt:message key="edit.car"/></a>
                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal">
                        <fmt:message key="delete"/>
                    </button>
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="confirm.del"/></h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="text-center"><fmt:message key="are.u.sure"/></div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="close"/> </button>
                                    <a href="controller?command=deleteCar&id=${sessionScope.currentCarToShow.id}"
                                       class="btn btn-danger" role="button" aria-pressed="true"><fmt:message key="delete"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
    </div>
    <div class="col"></div>
</div>
</body>
</html>
