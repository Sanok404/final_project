<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 03.10.2022
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="content"/>


</head>
<header>
    <%@include file="WEB-INF/include/nav_bar.jspf" %>
</header>
<body>
<div class="row">
    <p></p>
</div>
<div class="row">
    <div class="col-1"></div>
    <div class="col-10">
        <c:if test="${sessionScope.adminContent=='users'}">
            <c:if test="${sessionScope.currentUser.role=='ADMIN'}">
                <div class="container">
                    <h2><fmt:message key="all.users"/></h2>
                    <p class="text-success">${sessionScope.updateMessage}</p>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th><fmt:message key="email"/></th>
                            <th><fmt:message key="firstname"/></th>
                            <th><fmt:message key="lastname"/></th>
                            <th><fmt:message key="role"/></th>
                            <th><fmt:message key="edit.user"/></th>
                            <th><fmt:message key="block.unblock"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${sessionScope.users}">
                            <tr>
                                <td>${user.getId()}</td>
                                <td>${user.getEmail()}</td>
                                <td>${user.getFirstName()}</td>
                                <td>${user.getLastName()}</td>
                                <td>
                                    <c:if test="${user.getRole()=='ADMIN'}">
                                        <fmt:message key="admin"/>
                                    </c:if>
                                    <c:if test="${user.getRole()=='MANAGER'}">
                                        <fmt:message key="manager"/>
                                    </c:if>
                                    <c:if test="${user.getRole()=='CLIENT'}">
                                        <fmt:message key="client"/>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="controller?command=editUser&email=${user.email}&page=${sessionScope.page}&pageSize=${sessionScope.pageSize}"><fmt:message
                                            key="edit"/> </a>
                                </td>
                                <td><c:if test="${user.id!=sessionScope.currentUser.id}">
                                    <form action="controller?command=changeBlockStatus&email=${user.email}&page=${sessionScope.page}&pageSize=${sessionScope.pageSize}"
                                          method="post">
                                        <button class="btn btn-primary" type="submit">
                                            <c:if test="${user.isBlockStatus()}">
                                                <fmt:message key="unblock"/>
                                            </c:if>
                                            <c:if test="${!user.isBlockStatus()}">
                                                <fmt:message key="block"/>
                                            </c:if>
                                        </button>
                                    </form>
                                </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                            <a href="controller?command=showAllUsers&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                        </c:forEach>
                    </div>

                </div>
                <hr>
            </c:if>
        </c:if>

        <c:if test="${sessionScope.adminContent=='cars'||sessionScope.adminContent==null}">
            <div class="container">
                <div class="row">
                    <div class="col-3">
                        <fmt:message key="get.car.by"/>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <c:if test="${sessionScope.selectByGroup==null}">
                                    <fmt:message key="not.selected"/>
                                </c:if>
                                <c:if test="${sessionScope.selectByGroup!=null}">
                                    ${sessionScope.choose}
                                </c:if>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="controller?command=getAllCarClass"><fmt:message
                                        key="car.class"/> </a>
                                <a class="dropdown-item" href="controller?command=getAllBrands"><fmt:message
                                        key="brand"/> </a>
                            </div>
                        </div>
                    </div>

                    <div class="col-3">
                        <fmt:message key="value"/>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <c:if test="${sessionScope.chooseValue==null}">
                                    <fmt:message key="not.selected"/>
                                </c:if>
                                <c:if test="${sessionScope.chooseValue!=null}">
                                    ${sessionScope.chooseValue}
                                </c:if>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <c:forEach var="value" items="${sessionScope.selectByGroup}">
                                    <c:if test="${sessionScope.choose=='Brand'}">
                                        <a class="dropdown-item"
                                           href="controller?command=getAllCarsByBrand&brand=${value}&page=1&pageSize=6">${value}</a>
                                    </c:if>
                                    <c:if test="${sessionScope.choose=='Car Class'}">
                                        <a class="dropdown-item"
                                           href="controller?command=getAllCarsByClass&carClass=${value}&page=1&pageSize=6">${value}</a>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="col-6">
                        <fmt:message key="order.by"/>
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton6"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <c:if test="${sessionScope.orderBy==null}">
                                    <fmt:message key="not.selected"/>
                                </c:if>
                                <c:if test="${sessionScope.orderBy!=null}">
                                    ${sessionScope.orderBy}
                                </c:if>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton6">
                                <a class="dropdown-item"
                                   href="controller?command=getAllCarsOrderByPriceAsc&page=1&pageSize=6"><fmt:message
                                        key="sort.by.incr.price"/> </a>
                                <a class="dropdown-item"
                                   href="controller?command=getAllCarsOrderByPriceDesc&page=1&pageSize=6"><fmt:message
                                        key="sort.by.decr.price"/> </a>
                                <a class="dropdown-item"
                                   href="controller?command=getAllCarsOrderByBrandAsc&page=1&pageSize=6"><fmt:message
                                        key="sort.by.brand.A.Z"/> </a>
                                <a class="dropdown-item"
                                   href="controller?command=getAllCarsOrderByBrandDesc&page=1&pageSize=6"><fmt:message
                                        key="sort.by.brand.Z.A"/> </a>
                            </div>
                        </div>
                    </div>
                </div>
                <h2><fmt:message key="all.cars"/>
                    <c:if test="${sessionScope.chooseValue!=null}">
                        by ${sessionScope.chooseValue}
                    </c:if>
                </h2>

                <c:if test="${sessionScope.currentUser.role=='ADMIN'}">
                    <p></p>
                    <a href="controller?command=buttonAddNewCar" class="btn btn-primary btn-lg" role="button"
                       aria-pressed="true"><fmt:message key="add.new.car"/> </a>
                    <p></p>
                </c:if>

                <div class="card-deck">
                    <c:forEach var="car" items="${sessionScope.allCars}">
                        <div class="col-md-4">
                            <div class="card" style="width: 18rem;">
                                <img src="${pageContext.request.contextPath}${car.imageUrl}" class="card-img-top"
                                     alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">${car.brand} ${car.model}</h5>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><fmt:message
                                            key="transmission"/>: ${car.carTransmission}</li>
                                    <li class="list-group-item"><fmt:message key="price.per.day"/>: ${car.price}</li>
                                    <li class="list-group-item"><fmt:message key="car.class"/>: ${car.carClass}</li>
                                    <li class="list-group-item"><fmt:message key="price.driver.service"/></li>
                                </ul>
                                <div class="card-body">
                                    <a href="controller?command=showCarCardPage&id=${car.id}"
                                       class="btn btn-primary"
                                       role="button"
                                       aria-pressed="true"><fmt:message key="more.info"/> </a>
                                    <hr>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="text-center">
                <c:if test="${sessionScope.queryVariations=='allCarsQuery'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=showAllCars&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.queryVariations=='allCarsByBrandQuery'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=getAllCarsByBrand&brand=${sessionScope.chooseValue}&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.queryVariations=='allCarsByClassQuery'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=getAllCarsByClass&carClass=${sessionScope.chooseValue}&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.queryVariations=='carsOrderByPriceAsc'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=getAllCarsOrderByPriceAsc&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.queryVariations=='carsOrderByPriceDesc'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=getAllCarsOrderByPriceDesc&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.queryVariations=='carsOrderByBrandAsc'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=getAllCarsOrderByBrandAsc&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.queryVariations=='carsOrderByBrandDesc'}">
                    <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                        <a href="controller?command=getAllCarsOrderByBrandDesc&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                    </c:forEach>
                </c:if>
            </div>
        </c:if>
        <c:if test="${sessionScope.currentUser!=null}">
            <c:if test="${sessionScope.adminContent=='orders'}">
                <div class="container">
                    <h2><fmt:message key="all.orders"/></h2>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th><fmt:message key="car"/></th>
                            <th><fmt:message key="client"/></th>
                            <th><fmt:message key="passport.info"/></th>
                            <th><fmt:message key="driver.service"/></th>
                            <th><fmt:message key="date.start"/></th>
                            <th><fmt:message key="date.end"/></th>
                            <th><fmt:message key="cost"/></th>
                            <th><fmt:message key="status"/></th>
                            <th><fmt:message key="more.options"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${sessionScope.allOrders}">
                            <tr>
                                <td>${order.getId()}</td>
                                <td>${order.getCar().getBrand()} ${order.getCar().getModel()}</td>
                                <td>${order.getUser().getFirstName()} ${order.getUser().getLastName()}</td>
                                <td>${order.getSeriesAndNumberOfThePassport()}</td>
                                <td>
                                    <c:if test="${order.isWithADriver()==true}">
                                        <fmt:message key="yes"/>
                                    </c:if>
                                    <c:if test="${order.isWithADriver()==false}">
                                        <fmt:message key="no"/>
                                    </c:if>
                                </td>
                                <td>${order.getDateStart()}</td>
                                <td>${order.getDateEnd()}</td>
                                <td>${order.getCost()}</td>
                                <td>
                                    <c:if test="${order.getStatus()=='AWAITING_PAYMENT'}">
                                        <p class="text-primary"><fmt:message key="awaiting.payment"/></p>
                                    </c:if>
                                    <c:if test="${order.getStatus()=='PAID'}">
                                        <p class="text-warning"><fmt:message key="paid"/> </p>
                                    </c:if>
                                    <c:if test="${order.getStatus()=='DENIED'}">
                                        <p class="text-danger"><fmt:message key="denied"/></p>
                                    </c:if>
                                    <c:if test="${order.getStatus()=='PAYMENT_FOR_DAMAGE'}">
                                        <p class="text-danger"><fmt:message key="payment.for.damage"/></p>
                                    </c:if>
                                    <c:if test="${order.getStatus()=='ORDER_COMPLETE'}">
                                        <p class="text-success"><fmt:message key="order.complete"/></p>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="controller?command=showCardOrderPage&id=${order.id}"
                                       class="btn btn-primary"
                                       role="button"
                                       aria-pressed="true"><fmt:message key="more.info"/> </a>
                                    <hr>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <c:forEach var="i" begin="1" end="${sessionScope.pageCount+1}">
                            <a href="controller?command=showAllOrders&page=${i}&pageSize=${sessionScope.pageSize}">${i}</a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

        </c:if>
        <c:if test="${sessionScope.adminContent=='aboutUs'}">
            <div class="text-center">
                PROJECT DESCRIPTION
                <br>
                The task of the final project is to develop a web application that supports the functionality according to the task variant.<br>
                Variants<br>
            </div>
            <table class="table table-hover">
                <tbody>
                <tr>
                    <td>
                        Car rental
                    </td>
                    <td>
                        There is a list of Cars in the system, for which it is necessary to implement:<br>
                        - choice by brand;<br>
                        - choice according to quality class;<br>
                        - sort by rental price;<br>
                        - sort by name.<br>
                        The customer registers in the system, chooses a car and makes a rental order. An unregistered customer cannot place an order. <br>
                        In the order data the client indicates passport data, option 'with driver' / 'without driver', lease term. <br>
                        The system generates an Invoice, which the client pays.
                        The manager reviews the order and may reject it, giving a reason. The manager also registers the return of the car, <br>
                        in case of car damage he issues an invoice for repairs through the system.<br>
                        The system administrator has the rights:<br>
                        - adding, deleting cars, editing car information;<br>
                        - blocking / unblocking- the user;<br>
                        - registration of managers in the system.
                    </td>
                </tr>
                </tbody>
            </table>
        </c:if>
        <c:if test="${sessionScope.adminContent=='contactInfo'}">
            <div class="text-center align-content-center">
                Created by: Oleksandr Prykhodko<br>
                tel: +38(096)-652-66-53<br>
                telegram: @Aleksandr_Prykhodko
                email: a.j.prykhodko@gmail.com

            </div>
        </c:if>
    </div>
    <div class="col-1"></div>
</div>
</body>
</html>