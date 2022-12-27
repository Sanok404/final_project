<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 14.10.2022
  Time: 15:05
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
<div class="row d-flex justify-content-center align-items-center h-100">
    <form action="controller" method="post">
        <input name="command" value="updateUser" type="hidden">
        <input name="id" value="${sessionScope.userToEdit.id}" type="hidden">
        <h3 class="text-center"><fmt:message key="edit.user"/></h3>
        <p></p>
        <p></p>
        <div class="form-outline mb-4">
            <label class="form-label" for="form2Example1"><fmt:message key="email"/> </label>
            <input type="email" id="form2Example1" class="form-control" name="email"
                   value="${sessionScope.userToEdit.email}"/>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form2Example3"><fmt:message key="firstname"/> </label>
            <input id="form2Example3" class="form-control" name="firstName"
                   value="${sessionScope.userToEdit.firstName}"/>
        </div>
        <div class="form-outline mb-4">
            <label class="form-label" for="form2Example4"><fmt:message key="lastname"/> </label>
            <input id="form2Example4" class="form-control" name="lastName"
                   value="${sessionScope.userToEdit.lastName}"/>
        </div>
        <div class="form-group">
            <label> <fmt:message key="role"/>
                <select class="custom-select" required name="role">
                    <option value="${sessionScope.userToEdit.role}">
                        <c:if test="${sessionScope.userToEdit.role=='ADMIN'}">
                            <fmt:message key="admin"/>
                        </c:if>
                        <c:if test="${sessionScope.userToEdit.role=='MANAGER'}">
                            <fmt:message key="manager"/>
                        </c:if>
                        <c:if test="${sessionScope.userToEdit.role=='CLIENT'}">
                            <fmt:message key="client"/>
                        </c:if>
                    </option>
                    <c:if test="${sessionScope.userToEdit.role == 'ADMIN'}">
                        <option value="MANAGER"><fmt:message key="manager"/></option>
                        <option value="CLIENT"><fmt:message key="client"/></option>
                    </c:if>
                    <c:if test="${sessionScope.userToEdit.role == 'CLIENT'}">
                        <option value="ADMIN"><fmt:message key="admin"/></option>
                        <option value="MANAGER"><fmt:message key="manager"/></option>

                    </c:if>
                    <c:if test="${sessionScope.userToEdit.role == 'MANAGER'}">
                        <option value="ADMIN"><fmt:message key="admin"/></option>
                        <option value="CLIENT"><fmt:message key="client"/></option>
                    </c:if>
                </select>
            </label>
        </div>
        <button type="submit" class="btn btn-primary btn-block mb-4" value="Update"><fmt:message key="save.changes"/> </button>
    </form>
</div>
</body>
</html>
