<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 05.09.2021
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<html>--%>
<%--<head>--%>
<%--    <style>--%>
<%--        table {--%>
<%--            border-collapse: collapse;--%>
<%--            width: 50%;--%>
<%--        }--%>

<%--        th, td {--%>
<%--            text-align: left;--%>
<%--            padding: 8px;--%>
<%--        }--%>

<%--        tr:nth-child(even) {--%>
<%--            background-color: #D6EEEE;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>

<%--<h2>--%>
<%--    Masters and Services--%>
<%--</h2>--%>
<%--<body>--%>
<%--    <div class="container">--%>
<%--        <div>--%>
<%--            <table border="1" cellpadding="5" cellspacing="1">--%>
<%--                <tr>--%>
<%--                    &lt;%&ndash;                    <th>ID</th>&ndash;%&gt;--%>
<%--                    <th>ID</th>--%>
<%--                    <th>Master ID</th>--%>
<%--                    <th>Master Name</th>--%>
<%--                    <th>Master Surname</th>--%>
<%--                    <th>Service ID</th>--%>
<%--                    <th>Service</th>--%>
<%--                    <th>Service Duration</th>--%>
<%--                    <th>Service Price</th>--%>
<%--                    <th>Master rate</th>--%>
<%--                    <th>See timetable</th>--%>
<%--                </tr>--%>
<%--                <c:forEach items="${msList}" var="ms" varStatus="loop">--%>
<%--                    <tr>--%>
<%--                        <td>${ms.id}</td>--%>
<%--                        <td>${ms.master.id}</td>--%>
<%--                        <td>${ms.master.name}</td>--%>
<%--                        <td>${ms.master.surname}</td>--%>
<%--                        <td>${ms.service.id}</td>--%>
<%--                        <td>${ms.service.name}</td>--%>
<%--                        <td>${ms.service.duration}</td>--%>
<%--                        <td>${ms.service.price}</td>--%>
<%--                        <td>${ms.master.rate}</td>--%>
<%--                        <td>--%>
<%--                            <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}">--%>
<%--                               CHOOSE--%>
<%--                            </a>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
<%--            </table>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</body>--%>
<%--</html>--%>


<html lang="en">
<head>
    <title>Service List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="container">
        <h2>Masters and Services</h2>
        <p></p>
        <table class="table">
            <thead>
            <tr>
                <th>Master Name</th>
                <th>Master Surname</th>
                <th>Service</th>
                <th>Service Price</th>
                <th>Master rate</th>
                <c:if test="${role == 'client'}">
                    <th>See timetable</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${msList}" var="ms" varStatus="loop">
                <tr>
                    <td>${ms.master.name}</td>
                    <td>${ms.master.surname}</td>
                    <td>${ms.service.name}</td>
                    <td>${ms.service.price}</td>
                    <td>${ms.master.rate}</td>

                    <c:if test="${role == 'client'}">
                        <td>
                            <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}"
                               class="btn btn-success" role="button">Choose</a>
                        </td>
                    </c:if>
                        <%--                    <c:if test="${role != 'client'}">--%>
                        <%--                        <td>${role}</td>--%>
                        <%--                    </c:if>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <c:if test="${role == 'guest'}">
        <a href="/BeautySaloon_war/controller?command=logIn"
           class="btn btn-success" role="button">Register</a>
    </c:if>

    <c:if test="${role != 'guest'}">
        <a href="/BeautySaloon_war/controller?command=logOut"
           class="btn btn-success" role="button">Log Out</a>
    </c:if>

</body>
</html>
