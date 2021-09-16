<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 10.09.2021
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client Cabinet</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Client's Cabinet</h2>
        <div class="row">
            <div class="col-sm-3 col-md-2">
                <p>Login: ${client.login}</p>
                <p>Name: ${client.name}</p>
                <p>Surname: ${client.surname}</p>
                <p>Email: ${client.email}</p>
                <a href="/BeautySaloon_war/controller?command=logOut"
                   class="btn btn-danger" role="button">Log Out</a>
                <p></p>
                <a href="/BeautySaloon_war/controller?command=showMasterServices" role="button"
                   class="btn btn-success">Services</a>
                <p></p>
            </div>
            <div class="col-sm-9 col-md-8">
                <h3>Your Visits</h3>
                <table class="table">
                    <tr>
                        <th>Week Day</th>
                        <th>Date</th>
                        <th>Time Slot</th>
                        <th>Service</th>
                        <th>Master</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${clientMeetings}" var="cell" varStatus="loop">
                        <tr>
                            <td>${cell.dateTime.dayOfWeek}</td>
                            <td nowrap>${cell.dateTime.month}, ${cell.dateTime.dayOfMonth}</td>
                            <td>${cell.dateTime.hour} : 00</td>
                            <td>${cell.catalog.service.name}</td>
                            <td>${cell.catalog.master.name} ${cell.catalog.master.surname}</td>
                            <td>${cell.condition}</td>
                            <td>
                                <c:if test="${cell.reviewId == 0 and (cell.condition eq 'DONE' or cell.condition eq 'PAID')}">
                                    <a href="/BeautySaloon_war/controller?command=leaveReview&meetingId=${cell.id}"
                                       class="btn btn-primary" role="button">
                                        Leave Feedback
                                    </a>
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
