<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 16.10.2022
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!Doctype html>
<html>
<head>
    <title>Add new car</title>
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
<c:if test="${sessionScope.currentUser==null}">
    <jsp:forward page="index.jsp"/>
</c:if>
<c:if test="${sessionScope.currentUser!=null}">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <div class="row d-md-block justify-content-center align-items-center h-100">
                <h3 class="text-center"><fmt:message key="add.new.car"/> </h3>
                <p></p>
                <p></p>
                <c:if test="${sessionScope.newCarImageUrl==null}">
                    <form action="controller" method="post" enctype="multipart/form-data">
                        <input name="command" value="getNewCarImageUrl" type="hidden">
                        <input type="file" name="file" required>
                        <button type="submit" class="btn btn-primary"><fmt:message key="upload"/> </button>
                    </form>
                </c:if>
                <c:if test="${sessionScope.newCarImageUrl!=null}">
                    <fmt:message key="image.uploaded.success"/>
                    <p></p>
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
                    <input name="command" value="addNewCar" type="hidden">
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example1"><fmt:message key="car.brand"/> </label>
                        <input type="text" id="form2Example1" class="form-control" name="brand" required/>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example3"><fmt:message key="car.model"/> </label>
                        <input id="form2Example3" class="form-control" name="carModel" required/>
                    </div>
                    <div class="form-group">
                        <label> <fmt:message key="transmission"/>
                            <select class="custom-select" required name="transmission">
                                <option value="MANUAL"><fmt:message key="manual"/> </option>
                                <option value="AUTOMATIC"><fmt:message key="automatic"/> </option>
                            </select>
                        </label>
                    </div>
                    <div class="form-group">
                        <label> <fmt:message key="car.class"/>
                            <select class="custom-select" required name="carClass">
                                <option value="ECONOMY"><fmt:message key="economy"/> </option>
                                <option value="COMFORT"><fmt:message key="comfort"/> </option>
                                <option value="BUSINESS"><fmt:message key="business"/> </option>
                            </select>
                        </label>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example2"><fmt:message key="price.per.day"/> </label>
                        <input type="number" min="0" id="form2Example2" class="form-control" name="price" required/>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example5"><fmt:message key="info"/> </label>
                        <textarea id="form2Example5" class="form-control" name="info" rows="3" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block mb-6" value="Register"><fmt:message key="save.new.car"/> </button>

                </form>
            </div>
        </div>
        <div class="col"></div>
    </div>
</c:if>
</body>
</html>
