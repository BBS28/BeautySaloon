<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 10.09.2021
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

    <h2>REGISTER NEW ACCOUNT</h2>

    <form method="post" action="/BeautySaloon_war/controller?command=registration">
        <div class="col-sm-3 my-1">
            <label for="login">Login*:</label><br>
            <input type="text" name="login" id="login" class="form-control" placeholder="login">
        </div>

        <div class="col-sm-3 my-1">
            <label for="password">Password*:</label><br>
            <input type="password" name="password" id="password" class="form-control" placeholder="password">
        </div>

        <div class="col-sm-3 my-1">
            <label for="repeatPassword">Repeat Password*:</label><br>
            <input type="password" name="repeatPassword" id="repeatPassword" class="form-control" placeholder="repeat password">
        </div>

        <div class="col-sm-3 my-1">
            <label for="name">Name*:</label><br>
            <input type="text" name="name" id="name" class="form-control" placeholder="Name">
        </div>

        <div class="col-sm-3 my-1">
            <label for="surname">Surname*:</label><br>
            <input type="text" name="surname" id="surname" class="form-control" placeholder="Surname">
        </div>

        <div class="col-sm-3 my-1">
            <label for="email">Email*:</label><br>
            <input type="email" name="email" id="email" class="form-control" placeholder="user@mail.com">
        </div>

        <div class="text-danger">
            ${warn}
        </div>

        <input type="submit" class="btn btn-outline-success" value="Submit"><br><br>
    </form>

</body>
</html>
