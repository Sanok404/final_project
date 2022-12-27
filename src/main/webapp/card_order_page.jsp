<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 23.10.2022
  Time: 17:06
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
    <%@include file="WEB-INF/include/nav_bar.jspf" %>

</header>
<div class="row">
    <div class="col-3"></div>
    <div class="col-6 text-center justify-content-center">
        <c:if test="${sessionScope.currentUser==null}">
            <jsp:forward page="index.jsp"></jsp:forward>
        </c:if>
        <div class="row">
            <div class="col">
                <img src="${pageContext.request.contextPath}${requestScope.currentOrder.car.imageUrl}"
                     alt="car image">
            </div>
        </div>
        <div class="card-body">
            <h5 class="card-title">${requestScope.currentOrder.car.brand} ${requestScope.currentOrder.car.model}</h5>
            <div class="container">
                <h2><fmt:message key="order.details"/></h2>
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <td><fmt:message key="client"/>:</td>
                        <td>${requestScope.currentOrder.user.firstName} ${requestScope.currentOrder.user.lastName}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="passport.info"/>:</td>
                        <td>${requestScope.currentOrder.seriesAndNumberOfThePassport}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="driver.service"/>:</td>
                        <td>
                            <c:if test="${requestScope.currentOrder.withADriver==false}">
                                <fmt:message key="no"/>
                            </c:if>
                            <c:if test="${requestScope.currentOrder.withADriver==true}">
                                <fmt:message key="yes"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="period"/>:</td>
                        <td><fmt:message key="from"/> ${requestScope.currentOrder.dateStart} <fmt:message
                                key="to"/> ${requestScope.currentOrder.dateEnd}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="cost"/>:</td>
                        <td>
                            ${requestScope.currentOrder.cost}
                            <a href="${pageContext.request.contextPath}${sessionScope.pdfOrder}" class="btn btn-primary">FDF</a>
                        </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="order.status"/>:</td>
                        <c:if test="${sessionScope.currentUser.role=='CLIENT'}">

                            <td>
                                <c:if test="${requestScope.currentOrder.status=='AWAITING_PAYMENT'}">
                                    <p class="text-primary"><fmt:message key="awaiting.payment"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='PAID'}">
                                    <p class="text-warning"><fmt:message key="paid"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='DENIED'}">
                                    <p class="text-danger"><fmt:message key="denied"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='PAYMENT_FOR_DAMAGE'}">
                                    <p class="text-danger"><fmt:message key="payment.for.damage"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='ORDER_COMPLETE'}">
                                    <p class="text-success"><fmt:message key="order.complete"/></p>
                                </c:if>
                            </td>
                        </c:if>
                        <c:if test="${sessionScope.currentUser.role=='ADMIN'||sessionScope.currentUser.role=='MANAGER'}">
                            <td><c:if test="${requestScope.currentOrder.status=='AWAITING_PAYMENT'}">
                                <p class="text-primary"><fmt:message key="awaiting.payment"/></p>
                            </c:if>
                                <c:if test="${requestScope.currentOrder.status=='PAID'}">
                                    <p class="text-warning"><fmt:message key="paid"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='DENIED'}">
                                    <p class="text-danger"><fmt:message key="denied"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='PAYMENT_FOR_DAMAGE'}">
                                    <p class="text-danger"><fmt:message key="payment.for.damage"/></p>
                                </c:if>
                                <c:if test="${requestScope.currentOrder.status=='ORDER_COMPLETE'}">
                                    <p class="text-success"><fmt:message key="order.complete"/></p>
                                </c:if></td>
                            <td>
                                <form action="controller" method="post">
                                    <input name="command" value="setOrderStatus" type="hidden">
                                    <input name="orderId" value="${requestScope.currentOrder.id}" type="hidden">
                                    <div class="form-group">
                                        <label><fmt:message key="current.status"/>:
                                            <p></p>
                                            <c:if test="${requestScope.currentOrder.status=='AWAITING_PAYMENT'}">
                                                <p class="text-primary"><fmt:message key="awaiting.payment"/></p>
                                            </c:if>
                                            <c:if test="${requestScope.currentOrder.status=='PAID'}">
                                                <p class="text-warning"><fmt:message key="paid"/></p>
                                            </c:if>
                                            <c:if test="${requestScope.currentOrder.status=='DENIED'}">
                                                <p class="text-danger"><fmt:message key="denied"/></p>
                                            </c:if>
                                            <c:if test="${requestScope.currentOrder.status=='PAYMENT_FOR_DAMAGE'}">
                                                <p class="text-danger"><fmt:message key="payment.for.damage"/></p>
                                            </c:if>
                                            <c:if test="${requestScope.currentOrder.status=='ORDER_COMPLETE'}">
                                                <p class="text-success"><fmt:message key="order.complete"/></p>
                                            </c:if>
                                            <select class="custom-select" required name="newStatus">
                                                <option value="AWAITING_PAYMENT"><p class="text-primary"><fmt:message
                                                        key="awaiting.payment"/></p></option>
                                                <option value="PAID"><p class="text-warning"><fmt:message
                                                        key="paid"/></p></option>
                                                <option value="DENIED"><p class="text-danger"><fmt:message
                                                        key="denied"/></p></option>
                                                <option value="PAYMENT_FOR_DAMAGE"><p class="text-danger"><fmt:message
                                                        key="payment.for.damage"/></p></option>
                                                <option value="ORDER_COMPLETE"><p class="text-success"><fmt:message
                                                        key="order.complete"/></p></option>
                                            </select>
                                        </label>
                                    </div>
                                    <button class="btn btn-primary" type="submit"><fmt:message
                                            key="set.new.status"/></button>
                                </form>
                            </td>

                        </c:if>
                    </tr>
                    <c:if test="${requestScope.currentOrder.isDamaged()==true||sessionScope.currentUser.role=='ADMIN'
                    ||sessionScope.currentUser.role=='MANAGER'}">
                        <tr>
                            <td>
                                <fmt:message key="cost.of.damage.after.car.rental"/>:
                                <hr>
                                <fmt:message key="manager.comment"/>:
                            </td>
                            <td>
                                    ${requestScope.currentOrder.damageCost}
                                        <c:if test="${requestScope.currentOrder.isDamaged()==true}">
                                            <a href="${pageContext.request.contextPath}${sessionScope.pdfDamageOrder}" class="btn btn-primary">FDF</a>
                                        </c:if>
                                <hr>
                                    ${requestScope.currentOrder.managersComment}
                            </td>
                            <c:if test="${sessionScope.currentUser.role=='ADMIN'||sessionScope.currentUser.role=='MANAGER'}">
                                <td>
                                    <form action="controller" method="post">
                                        <input name="command" value="setInfoAboutDamage" type="hidden">
                                        <input name="orderId" value="${requestScope.currentOrder.id}" type="hidden">
                                        <div class="form-outline">
                                            <label class="form-label" for="form1"><fmt:message
                                                    key="cost.of.damage.after.car.rental"/>: </label>
                                            <input type="number" min="0" id="form1" class="form-control" name="damageCost"
                                                   required/>
                                        </div>
                                        <div class="form-outline">
                                            <label class="form-label" for="form2"><fmt:message key="comment"/>:</label>
                                            <textarea id="form2" class="form-control" name="managersComment" rows="3"
                                                      required></textarea>
                                        </div>
                                        <button class="btn btn-primary" type="submit"><fmt:message
                                                key="add.damage.info"/></button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="col-3"></div>
</div>
</body>
</html>
