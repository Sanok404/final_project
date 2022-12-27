<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 22.10.2022
  Time: 00:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
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
    <%@include file="WEB-INF/include/nav_bar.jspf" %>
</header>
<div class="row">
    <div class="col"></div>
    <div class="col">
        <div class="row d-md-block justify-content-center align-items-center h-100">
            <h3 class="text-center"><fmt:message key="edit.car"/></h3>
            <p></p>
            <p></p>
            <c:if test="${sessionScope.newCarImageUrl==null}">
                <div class="card-deck">
                    <div class="col-md-4">
                        <div class="card" style="width: 18rem;">
                            <img src="${pageContext.request.contextPath}${sessionScope.currentCarToShow.imageUrl}"
                                 class="card-img-top" alt="car image">
                        </div>
                    </div>
                </div>
                <form action="controller" method="post" enctype="multipart/form-data">
                    <input name="command" value="editCarImageUrl" type="hidden">
                    <input type="file" name="file">
                    <button type="submit" class="btn btn-primary"><fmt:message key="upload"/></button>
                </form>
            </c:if>
            <c:if test="${sessionScope.newCarImageUrl!=null}">
                <fmt:message key="image.uploaded.success"/>
                <p></p>
                <div class="card-deck">
                    <div class="col-md-4">
                        <div class="card" style="width: 18rem;">
                            <img src="${pageContext.request.contextPath}${sessionScope.newCarImageUrl}"
                                 class="card-img-top" alt="...">
                        </div>
                    </div>
                </div>
            </c:if>
            <form action="controller" method="post">
                <input name="command" value="editCar" type="hidden">
                <input name="id" value="${sessionScope.currentCarToShow.id}" type="hidden">
                <input name="carImageUrl" value="${sessionScope.currentCarToShow.imageUrl}" type="hidden">
                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example1"><fmt:message key="car.brand"/> </label>
                    <input type="text" id="form2Example1" class="form-control" name="brand"
                           value="${sessionScope.currentCarToShow.brand}"/>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example3"><fmt:message key="car.model"/> </label>
                    <input id="form2Example3" class="form-control" name="carModel"
                           value="${sessionScope.currentCarToShow.model}"/>
                </div>
                <div class="form-group">
                    <label> <fmt:message key="transmission"/>
                        <select class="custom-select" required name="transmission">
                            <option value="${sessionScope.currentCarToShow.carTransmission}">${sessionScope.currentCarToShow.carTransmission}</option>
                            <option value="MANUAL"><fmt:message key="manual"/> </option>
                            <option value="AUTOMATIC"><fmt:message key="automatic"/> </option>
                        </select>
                    </label>
                </div>
                <div class="form-group">
                    <label> <fmt:message key="car.class"/>
                        <select class="custom-select" required name="carClass">
                            <option value="${sessionScope.currentCarToShow.carClass}">${sessionScope.currentCarToShow.carClass}</option>
                            <option value="ECONOMY"><fmt:message key="economy"/> </option>
                            <option value="COMFORT"><fmt:message key="comfort"/> </option>
                            <option value="BUSINESS"><fmt:message key="business"/> </option>
                        </select>
                    </label>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example2"><fmt:message key="price.per.day"/> </label>
                    <input type="number" id="form2Example2" class="form-control" name="price"
                           value="${sessionScope.currentCarToShow.price}"/>
                </div>
                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example5"><fmt:message key="info"/> </label>
                    <textarea id="form2Example5" class="form-control" name="info"
                              rows="3">${sessionScope.currentCarToShow.infoAboutCar}</textarea>
                </div>
                <button type="submit" class="btn btn-primary btn-block mb-6" value="Register"><fmt:message key="save.changes"/> </button>

            </form>
        </div>
    </div>
    <div class="col"></div>
</div>
</body>
</html>
