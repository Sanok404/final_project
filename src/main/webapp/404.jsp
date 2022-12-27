<%--
  Created by IntelliJ IDEA.
  User: ajpry
  Date: 29.10.2022
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>404 Error Page</title>--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_error_404.css">--%>

<%--</head>--%>
<%--<body class="bg-purple">--%>

<%--<div class="stars">--%>

<%--    <div class="central-body">--%>
<%--        <img class="image-404" src="http://salehriaz.com/404Page/img/404.svg" width="300px">--%>
<%--        <a href="index.jsp" class="btn-go-home">GO BACK HOME</a>--%>
<%--    </div>--%>
<%--    <div class="objects">--%>
<%--        <img class="object_rocket" src="http://salehriaz.com/404Page/img/rocket.svg" width="40px">--%>
<%--        <div class="earth-moon">--%>
<%--            <img class="object_earth" src="http://salehriaz.com/404Page/img/earth.svg" width="100px">--%>
<%--            <img class="object_moon" src="http://salehriaz.com/404Page/img/moon.svg" width="80px">--%>
<%--        </div>--%>
<%--        <div class="box_astronaut">--%>
<%--            <img class="object_astronaut" src="http://salehriaz.com/404Page/img/astronaut.svg" width="140px">--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="glowing_stars">--%>
<%--        <div class="star"></div>--%>
<%--        <div class="star"></div>--%>
<%--        <div class="star"></div>--%>
<%--        <div class="star"></div>--%>
<%--        <div class="star"></div>--%>

<%--    </div>--%>

<%--</div>--%>

<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Not Found</title>

    <!-- Fonts -->
    <link rel="dns-prefetch" href="//fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">

    <!-- Styles -->
    <style>
        html, body {
            background-color: #fff;
            color: #636b6f;
            font-family: 'Nunito', sans-serif;
            font-weight: 100;
            height: 100vh;
            margin: 0;
        }

        .full-height {
            height: 100vh;
        }

        .flex-center {
            align-items: center;
            display: flex;
            justify-content: center;
        }

        .position-ref {
            position: relative;
        }

        .code {
            border-right: 2px solid;
            font-size: 36px;
            padding: 0 15px 0 15px;
            text-align: center;
        }

        .message {
            border-right: 2px solid;
            font-size: 25px;
            padding: 0 15px 0 15px;
            text-align: center;
        }
        .home{
            text-indent: 1em;
            font-size: 18px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="flex-center position-ref full-height">
    <div class="row">
        <div class="code">
            404
        </div>

        <div class="message" style="padding: 10px;">
            Not Found
        </div>
    </div>
    <div class="home">
        <a href="index.jsp" class="btn-go-home">GO BACK HOME</a>
    </div>
</div>

</body>
</html>
