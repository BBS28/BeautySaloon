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
    <title>Master Schedule</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<h2>
    Master: ${master.surname} ${master.name}
</h2>
<body>
    <div class="container">
        <div>
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
                            <td>FREE</td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </c:if>
                        <c:if test="${cell.value != null}">
                            <td>TAKEN</td>
                            <td>${cell.value.catalog.service.name}</td>
                            <td>${cell.value.client.name} ${cell.value.client.surname}</td>
                            <td>${cell.value.condition}</td>
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
