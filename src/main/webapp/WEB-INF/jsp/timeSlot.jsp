<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 06.09.2021
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
        <div class="row">
            <div class="col-sm-3 col-md-2">
                <h3>Master: ${ms.master.surname} ${ms.master.name}</h3>
                <h3>Service: ${ms.service.name}</h3>
                <p></p>
                <a href="/BeautySaloon_war/controller?command=showMasterServices" role="button"
                   class="btn btn-success">Services</a>
            </div>

            <div class="col-sm-9 col-md-8">
            <h3>Schedule</h3>
                <table class="table">
                    <tr>
                        <th>Week Day</th>
                        <th>Date</th>
                        <th>Time Slot</th>
                        <th>Register</th>
                    </tr>
                    <c:forEach items="${schedule}" var="cell" varStatus="loop">
                        <tr>
                            <td>${cell.key.dayOfWeek}</td>
                            <td>${cell.key.month}, ${cell.key.dayOfMonth}</td>
                            <td>${cell.key.hour} : 00</td>
                            <c:if test="${cell.value == false}">
                                <td>
                                    <a href="/BeautySaloon_war/controller?command=createMeeting&clientId=${sessionScope.accountID}&msId=${ms.id}&time=${cell.key}"
                                       class="btn btn-primary" role="button">
                                        CHOOSE THIS
                                    </a>
                                </td>
                            </c:if>
                            <c:if test="${cell.value == true}">
                                <td>TAKEN</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
