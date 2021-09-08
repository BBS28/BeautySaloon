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

<html>
<head>
    <meta charset="UTF-8">
    <title>Master Service List</title>
</head>
<body>
    <div class="container">
        <div>
            <table border="1" cellpadding="5" cellspacing="1">
                <tr>
                    <%--                    <th>ID</th>--%>
                    <th>ID</th>
                    <th>Master ID</th>
                    <th>Master Name</th>
                    <th>Master Surname</th>
                    <th>Service ID</th>
                    <th>Service</th>
                    <th>Service Duration</th>
                    <th>Service Price</th>
                    <th>Master rate</th>
                    <th>See timetable</th>
                </tr>
                <c:forEach items="${msList}" var="ms" varStatus="loop">
                    <tr>
                        <td>${ms.id}</td>
                        <td>${ms.master.id}</td>
                        <td>${ms.master.name}</td>
                        <td>${ms.master.surname}</td>
                        <td>${ms.service.id}</td>
                        <td>${ms.service.name}</td>
                        <td>${ms.service.duration}</td>
                        <td>${ms.service.price}</td>
                        <td>${ms.master.rate}</td>
                        <td>
                            <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}">
                                <fmt:message key="button.timetable"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
