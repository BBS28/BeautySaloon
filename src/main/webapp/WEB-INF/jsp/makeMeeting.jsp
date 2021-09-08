<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 07.09.2021
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="container">
        <div>
            <table border="1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>Week Day</th>
                    <th>MM</th>
                    <th>DD</th>
                    <th>Time Slot</th>
                    <th>msId</th>
                </tr>
                <c:set var="${msId}" >
                    <tr>
                        <td>${cell.key.dayOfWeek}</td>
                        <td>${cell.key.month}</td>
                        <td>${cell.key.dayOfMonth}</td>
                        <td>${cell.key.hour} : 00</td>
                        <c:if test="${cell.value == false}">
                            <td>
                                <a href="/BeautySaloon_war/controller?command=createMeeting&clientId=1&"></a>
                            </td>
                        </c:if>
                        <c:if test="${cell.value == true}">
                            <td>Taken</td>
                        </c:if>
                    </tr>
                </c:>
            </table>
        </div>
    </div>
</body>
</html>
