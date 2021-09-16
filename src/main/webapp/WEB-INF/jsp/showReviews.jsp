<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 16.09.2021
  Time: 1:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reviews</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="container">

        <div class="row">

            <div class="col-sm-3 col-md-2">
                <p></p>
                <a href="/BeautySaloon_war/controller?command=showMasterServices"
                   class="btn btn-success" role="button">Catalog</a>
                <p></p>
            </div>

            <div class="col-sm-9 col-md-8">

                <table class="table">
                    <tr>
                        <th>Client</th>
                        <th>Review</th>
                    </tr>

                    <c:forEach items="${reviewMap}" var="cell" varStatus="loop">
                        <tr>
                            <td>${cell.key.client.name}</td>
                            <td>${cell.value.text}</td>
                        </tr>
                    </c:forEach>
                </table>

            </div>

        </div>

    </div>

</body>
</html>
