<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 13.09.2021
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administrator Cabinet</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="container">
        <h2>Administrator's Cabinet</h2>
        <div class="row">

            <div class="col-sm-3 col-md-2">
                <p>Login: ${admin.login}</p>
                <p>Name: ${admin.name}</p>
                <p>Surname: ${admin.surname}</p>
                <p>Email: ${admin.email}</p>
                <a href="/BeautySaloon_war/controller?command=logOut"
                   class="btn btn-danger" role="button">Log Out</a>
                <p></p>
                <a href="/BeautySaloon_war/controller?command=showMasterServices" role="button"
                   class="btn btn-success">Services</a>
                <p></p>
            </div>

            <div class="col-sm-9 col-md-8">
                <h3>All Services</h3>
                <table class="table">
                    <tr>
                        <th>Date
                        <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=dateSort">
                            <span class="glyphicon glyphicon-sort-by-order"></span>
                        </a>
                        </th>
                        <th></th>
                        <th>Master
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=masterSort">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th>Service
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=serviceSort">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th>Client
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=clientSort">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th>Status</th>
                        <th></th>


                        <%--                        <th>Review</th>--%>
                    </tr>
                    <c:forEach items="${meetingList}" var="cell" varStatus="loop">
                        <tr>
                            <td>${cell.dateTime}</td>
                            <td>
                                <c:if test="${cell.dateTime > currentTime}">
                                <a href="/BeautySaloon_war/controller?command=changeTimeSlot&slotId=${cell.id}"
                                   class="btn btn-info" role="button">Change</a>
                                </c:if>
                            </td>
                            <td>${cell.catalog.master.name} ${cell.catalog.master.surname}</td>
                            <td>${cell.catalog.service.name}</td>
                            <td>${cell.client.name} ${cell.client.surname}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${cell.condition eq 'DONE' and cell.dateTime < currentTime}">
                                        <a href="/BeautySaloon_war/controller?command=paidDone&meetingId=${cell.id}&pageDate=${cell.dateTime}"
                                           class="btn btn-warning" role="button">${cell.condition}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${cell.condition eq 'PAID' and cell.dateTime < currentTime}">
                                                <a href="/BeautySaloon_war/controller?command=paidDone&meetingId=${cell.id}&pageDate=${cell.dateTime}"
                                                   class="btn btn-success" role="button">${cell.condition}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${cell.condition}
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:if test="${cell.condition eq 'ACTIVE'}">
                                    <a href="/BeautySaloon_war/controller?command=cancelMeeting&meetingId=${cell.id}&pageDate=${cell.dateTime}"
                                       class="btn btn-danger" role="button">Cancel</a>
                                </c:if>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
    </div>


</body>
</html>
