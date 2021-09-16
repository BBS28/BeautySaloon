<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 15.09.2021
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Time Slot</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">

        <div class="col-sm-3 col-md-2">
            <h4>Master: </h4><a>${meeting.catalog.master.surname} ${meeting.catalog.master.name}</a>
            <p></p>
            <h4>Service: </h4><a> ${meeting.catalog.service.name}</a>
            <p></p>
            <a href="/BeautySaloon_war/controller?command=adminCabinet" role="button"
               class="btn btn-danger">Cancel</a>
        </div>

        <div class="col-sm-9 col-md-8">
            <h2>Free Slots</h2>

            <table class="table">
                <tr>
                    <th>Week Day</th>
                    <th>Date</th>
                    <th>Time Slot</th>
                    <th>Register</th>
                </tr>
                <c:forEach items="${schedule}" var="slot" varStatus="loop">
                    <tr>
                        <td>${slot.dayOfWeek}</td>
                        <td>${slot.month}, ${slot.dayOfMonth}</td>
                        <td>${slot.hour} : 00</td>
                        <td>
                            <form method="post" action="/BeautySaloon_war/controller?command=changeTimeSlot">
                                <input type="hidden" name="slot" value="${slot}">
                                <input type="hidden" name="meetingId" value="${meeting.id}">
                                <input type="submit" class="btn btn-success" value="Choose new">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
