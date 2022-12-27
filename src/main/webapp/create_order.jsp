<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 22.10.2022
  Time: 19:13
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
    <script>
        $(function () {
            $("#datepicker").datepicker({
                minDate: 0,
                dateFormat: "dd-mm-yy"
            });
        });
    </script>
    <script>
        $(function () {
            $("#datepicker1").datepicker({
                minDate: 0,
                dateFormat: "dd-mm-yy"
            });
        });
    </script>

</head>
<body>
<header>
    <%@include file="WEB-INF/include/nav_bar.jspf" %>
</header>
<div class="row">
    <div class="col"></div>
    <div class="col">
        <div class="row">
            <div class="col">
                <img src="${pageContext.request.contextPath}${sessionScope.currentCarToShow.imageUrl}"
                     alt="car image">
            </div>
            <div class="col">
                <div class="align-baseline">
                    <h6>
                        <fmt:message key="transmission"/> : ${sessionScope.currentCarToShow.carTransmission}
                    </h6>
                    <hr>
                    <h6>
                        <fmt:message key="price.per.day"/> : ${sessionScope.currentCarToShow.price}
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
        <form action="controller" method="get">
            <input name="command" value="confirmOrder" type="hidden">
            <input name="carId" value="${sessionScope.currentCarToShow.id}" type="hidden">
            <input name="userId" value="${sessionScope.currentUser.id}" type="hidden">
            <label class="form-label" for="form2Example1">
                <fmt:message key="enter.passport.info"/> </label>
            <input type="text" id="form2Example1" class="form-control" name="passport" required/>
            <div class="form-group">
                <label> <fmt:message key="do.you.need.a.driver"/>
                    <select class="custom-select" required name="withADriver">
                        <option value="Yes"><fmt:message key="yes"/></option>
                        <option value="No"><fmt:message key="no"/></option>
                    </select>
                </label>
            </div>
            <p class="text-danger">
                ${requestScope.incorrectDate}
            </p>
            <div class="form-group">
                <label for="datepicker"><fmt:message key="when.do.we.start"/> </label>
                <input type="text" id="datepicker" name="dateStart">
            </div>
            <div class="form-group">

                <label for="datepicker1"><fmt:message key="when.do.we.finish"/> </label>
                <input type="text" id="datepicker1" name="dateEnd">
            </div>
            <button type="submit" class="btn btn-primary" value="Register"><fmt:message key="make.order"/></button>
        </form>
    </div>
    <div class="col"></div>
</div>
</body>
</html>
