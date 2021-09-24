<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">
        <h2><fmt:message key="mastersCabinet"/></h2>

        <div class="col-sm-3 col-md-2">
            <p><b><fmt:message key="login"/>:</b> ${master.login}</p>
            <p><b><fmt:message key="name"/>:</b> ${master.name}</p>
            <p><b><fmt:message key="surname"/>:</b> ${master.surname}</p>
            <p><b>Email:</b> ${master.email}</p>
        </div>

        <div class="col-sm-9 col-md-8">
            <h3><fmt:message key="schedule"/></h3>
            <table class="table">
                <tr>
                    <th><fmt:message key="weekDay"/></th>
                    <th><fmt:message key="date"/></th>
                    <th><fmt:message key="time"/></th>
                    <th><fmt:message key="service"/></th>
                    <th><fmt:message key="condition"/></th>
                    <th><fmt:message key="client"/></th>
                    <th><fmt:message key="status"/></th>
                </tr>
                <c:forEach items="${masterDaySchedule}" var="cell" varStatus="loop">
                    <tr>
                        <td><fmt:message key="${cell.key.dayOfWeek}"/></td>
                        <td><fmt:message key="${cell.key.month}"/>, ${cell.key.dayOfMonth}</td>
                        <td>${cell.key.hour} : 00</td>
                        <c:if test="${cell.value == null}">
                            <td></td>
                            <td><fmt:message key="free"/></td>
                            <td></td>
                            <td></td>
                        </c:if>
                        <c:if test="${cell.value != null}">
                            <td><fmt:message key="${cell.value.catalog.service.name}"/></td>
                            <td><fmt:message key="taken"/></td>
                            <td>${cell.value.client.name} ${cell.value.client.surname}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${cell.value.condition eq 'ACTIVE' and cell.key < currentTime}">
                                        <a href="/BeautySaloon_war/controller?command=activeDone&meetingId=${cell.value.id}&daysFromNow=${daysFromNow}"
                                           class="btn btn-primary" role="button"><fmt:message key="${cell.value.condition}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${cell.value.condition eq 'DONE' and cell.key < currentTime}">
                                                <a href="/BeautySaloon_war/controller?command=activeDone&meetingId=${cell.value.id}&daysFromNow=${daysFromNow}"
                                                   class="btn btn-warning" role="button"><fmt:message key="${cell.value.condition}"/></a>
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
               class="btn btn-info" role="button"><fmt:message key="previousDay"/></a>
            <a href="/BeautySaloon_war/controller?command=masterSchedule&scheduleDay=${dayOfWeek eq 'SATURDAY' ? daysFromNow + 2 : daysFromNow + 1}"
               class="btn btn-info" role="button"><fmt:message key="nextDay"/></a>
        </div>

    </div>
</body>
</html>
