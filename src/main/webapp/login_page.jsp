<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 06.10.2022
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="content"/>
<header>
    <%@include file="WEB-INF/include/nav_bar.jspf"%>

</header>
<c:if test="${sessionScope.currentUser!=null}">
    <jsp:forward page="index.jsp"></jsp:forward>
</c:if>
<div class="row d-flex justify-content-center align-items-center h-100">
    <form action="controller" method="post">
        <input name="command" value="login" type="hidden">
        <h3 class="text-center"><fmt:message key="login"/></h3>
        <p></p>
        <p></p>
        <p class="text-success"> ${sessionScope.afterRegMessage}</p>
        <div class="form-outline mb-4">
            <label class="form-label" for="form2Example1"><fmt:message key="email"/> </label>
            <input type="email" id="form2Example1" class="form-control" name="email"/>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form2Example2"><fmt:message key="password"/> </label>
            <input type="password" id="form2Example2" class="form-control" name="password"/>
            <p class="text-danger">
                ${sessionScope.message}
            </p>
        </div>
        <button type="submit" class="btn btn-primary btn-block mb-4" value="Login"><fmt:message key="sign.in"/> </button>
    </form>
</div>
</body>
</html>
