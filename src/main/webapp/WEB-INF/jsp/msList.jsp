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

        <h2>Our Services And Masters</h2>

        <div class="row">

            <div class="col-sm-3 col-md-2">

                <c:if test="${role == 'guest'}">
                    <a href="/BeautySaloon_war/controller?command=logIn"
                       class="btn btn-success" role="button">Log In</a>
                </c:if>
                <p></p>
                <c:if test="${role != 'guest'}">
                    <a href="/BeautySaloon_war/controller?command=logOut"
                       class="btn btn-danger" role="button">Log Out</a>
                </c:if>
                <p></p>

                <c:if test="${role == 'master'}">
                    <a href="/BeautySaloon_war/controller?command=masterSchedule"
                       class="btn btn-success" role="button">Schedule</a>
                </c:if>
                <p></p>

                <c:if test="${role == 'client'}">
                    <a href="/BeautySaloon_war/controller?command=clientCabinet"
                       class="btn btn-success" role="button">Cabinet</a>
                </c:if>
                <p></p>

            </div>

            <div class="col-sm-9 col-md-8">

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
                            <td>
                                <fmt:formatNumber value="${ms.master.rate}" pattern="#.#"/>
                            </td>

                            <c:if test="${role == 'client'}">
                                <td>
                                    <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}"
                                       class="btn btn-primary" role="button">Choose</a>
                                </td>
                            </c:if>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>


            </div>

        </div>

    </div>
</body>
</html>
