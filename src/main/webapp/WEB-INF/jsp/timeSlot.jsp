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
    <title>Schedule</title>
</head>
<body>
    <div class="container">
        <div>
            <table border="1" cellpadding="5" cellspacing="1">
                <tr>
                    <%--                    <th>ID</th>--%>
                    <th>Week Day</th>
                    <th>MM</th>
                    <th>DD</th>
                    <th>Time Slot</th>
                    <th>Register</th>
                </tr>
                <c:forEach items="${schedule}" var="cell" varStatus="loop">
                    <tr>
                        <td>${cell.key.dayOfWeek}</td>
                        <td>${cell.key.month}</td>
                        <td>${cell.key.dayOfMonth}</td>
                        <td>${cell.key.hour} : 00</td>
                        <c:if test="${cell.value == false}">
                            <td>
                                <a href="/BeautySaloon_war/controller?command=createMeeting&clientId=1&msId=${ms.id}&time=${cell.key}">
                                    Order
                                </a>
                            </td>
                        </c:if>
                        <c:if test="${cell.value == true}">
                            <td>Taken</td>
                        </c:if>
                        </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
