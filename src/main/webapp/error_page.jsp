<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 02.10.2022
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<div>
    <div class="text-center align-content-center">
        <p class="fs-3"> <span class="text-danger">Opps!</span> Something went wrong.</p>
        <p class="lead">
            ${sessionScope.exception}<br>
            Return to home page.
        </p>
        <a href="index.jsp" class="btn btn-primary">Go Home</a>
    </div>
</div>



</body>
</html>
