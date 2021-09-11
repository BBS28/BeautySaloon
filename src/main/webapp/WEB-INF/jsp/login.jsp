<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 08.09.2021
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

    <h2>LOGIN</h2>

    <form method="post" action="/BeautySaloon_war/controller?command=logIn">
        <div class="col-sm-3 my-1">
            <label for="login">Login:</label><br>
            <input type="text" name="login" id="login" class="form-control" placeholder="login"><br>
        </div>

        <div class="col-sm-3 my-1">
            <label for="password">Password:</label><br>
            <input type="password" name="password" id="password" class="form-control" placeholder="password"><br><br>
        </div>

        <input type="submit" class="btn btn-outline-success" value="Submit">
        <a class="text-danger">
            ${warn}!
        </a><br><br>
    </form>
    <a href="/BeautySaloon_war/controller?command=registration" role="button"
       class="btn btn-outline-info">Registration</a>

    <br>
    <br>
    <a href="/BeautySaloon_war/controller?command=showMasterServices" role="button"
       class="btn btn-outline-secondary">Guest</a>
</body>
</html>


