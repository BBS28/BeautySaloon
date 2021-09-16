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

                <c:if test="${role == 'guest' or role == null}">
                    <a href="/BeautySaloon_war/controller?command=logIn"
                       class="btn btn-success" role="button">Log In</a>
                </c:if>
                <p></p>
                <c:if test="${role == 'master' or role == 'admin' or role == 'client'}">
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

                <c:if test="${role == 'admin'}">
                    <a href="/BeautySaloon_war/controller?command=adminCabinet"
                       class="btn btn-success" role="button">Cabinet</a>
                </c:if>
                <p></p>

                <a href="/BeautySaloon_war/controller?command=showReviews"
                   class="btn btn-info" role="button">Reviews</a>
                <p></p>

            </div>

            <div class="col-sm-9 col-md-8">

                <table class="table">

                    <thead>

                    <tr>
                        <th>
                            Master
                            <a href="/BeautySaloon_war/controller?command=showMasterServices&sort=master&sFilter=${sFilter}&mFilter=${mFilter}">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th>
                            Master rate
                            <a href="/BeautySaloon_war/controller?command=showMasterServices&sort=rate&sFilter=${sFilter}&mFilter=${mFilter}">
                                <span class="glyphicon glyphicon-sort-by-order-alt"></span>
                            </a>
                        </th>
                        <th>Service</th>
                        <th>Service Price</th>

                        <c:if test="${role == 'client'}">
                            <th>See timetable</th>
                        </c:if>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach items="${msList}" var="ms" varStatus="loop">

                        <tr>
                            <td>
                                  <a href="/BeautySaloon_war/controller?command=showMasterServices&mFilter=${ms.master.id}">
                                          ${ms.master.name} ${ms.master.surname}
                                  </a>
                            <td>
                                <span class="glyphicon glyphicon-star"></span>
                                <fmt:formatNumber value="${ms.master.rate}" pattern="#.#"/>
                            </td>
                            <td>
                                <a href="/BeautySaloon_war/controller?command=showMasterServices&sFilter=${ms.service.id}">
                                        ${ms.service.name}
                                </a>
                            </td>
                            <td><fmt:formatNumber value="${ms.service.price}" pattern="#.00"/></td>
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

                <p>
                    <a href="/BeautySaloon_war/controller?command=showMasterServices"
                       class="btn btn-default" role="button">Clear Filters</a>
                </p>

            </div>

        </div>

    </div>
</body>
</html>
