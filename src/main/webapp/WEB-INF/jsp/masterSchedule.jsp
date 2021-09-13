<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 09.09.2021
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Master's Schedule</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Master's cabinet</h2>

        <div class="col-sm-3 col-md-2">
            <p>Login: ${master.login}</p>
            <p>Name: ${master.name}</p>
            <p>Surname: ${master.surname}</p>
            <p>Email: ${master.email}</p>
            <p href="/BeautySaloon_war/controller?command=logOut"
               class="btn btn-danger" role="button">Log Out</p>
            <p></p>
            <a href="/BeautySaloon_war/controller?command=showMasterServices" role="button"
               class="btn btn-success">Services</a>
            <p></p>
        </div>

        <div class="col-sm-9 col-md-8">
            <h2>Your Schedule</h2>
            <table class="table">
                <tr>
                    <th>Week Day</th>
                    <th>Date</th>
                    <th>Time Slot</th>
                    <th>Service</th>
                    <th>Condition</th>
                    <th>Client</th>
                    <th>Status</th>
                </tr>
                <c:forEach items="${masterDaySchedule}" var="cell" varStatus="loop">
                    <tr>
                        <td>${cell.key.dayOfWeek}</td>
                        <td>${cell.key.month}, ${cell.key.dayOfMonth}</td>
                        <td>${cell.key.hour} : 00</td>
                        <c:if test="${cell.value == null}">
                            <td></td>
                            <td>FREE</td>
                            <td></td>
                            <td></td>
                        </c:if>
                        <c:if test="${cell.value != null}">
                            <td>${cell.value.catalog.service.name}</td>
                            <td>TAKEN</td>
                            <td>${cell.value.client.name} ${cell.value.client.surname}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${cell.value.condition eq 'ACTIVE' and cell.key < currentTime}">
                                        <a href="/BeautySaloon_war/controller?command=doneService&meetingId=${cell.value.id}&daysFromNow=${daysFromNow}"
                                           class="btn btn-primary" role="button">${cell.value.condition}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${cell.value.condition eq 'DONE' and cell.key < currentTime}">
                                                <a href="/BeautySaloon_war/controller?command=doneService&meetingId=${cell.value.id}&daysFromNow=${daysFromNow}"
                                                   class="btn btn-warning" role="button">${cell.value.condition}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${cell.value.condition}
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <a href="/BeautySaloon_war/controller?command=masterSchedule&scheduleDay=${dayOfWeek eq 'MONDAY' ? daysFromNow - 2 : daysFromNow - 1}"
               class="btn btn-info" role="button">Previous Day</a>
            <a href="/BeautySaloon_war/controller?command=masterSchedule&scheduleDay=${dayOfWeek eq 'SATURDAY' ? daysFromNow + 2 : daysFromNow + 1}"
               class="btn btn-info" role="button">Next Day</a>
        </div>

    </div>
</body>
</html>
