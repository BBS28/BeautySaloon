<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">
        <div class="row">
            <div class="col-sm-3 col-md-2">
                <p><b><fmt:message key="master"/>:</b> ${ms.master.surname} ${ms.master.name}</p>
                <p><b><fmt:message key="service"/>:</b> ${ms.service.name}</p>
                <p></p>

            </div>

            <div class="col-sm-9 col-md-8">
                <h2><fmt:message key="schedule"/></h2>
                <table class="table">
                    <tr>
                        <th><fmt:message key="weekDay"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><fmt:message key="register"/></th>
                    </tr>
                    <c:forEach items="${schedule}" var="cell" varStatus="loop">
                        <tr>
                            <td>${cell.key.dayOfWeek}</td>
                            <td>${cell.key.month}, ${cell.key.dayOfMonth}</td>
                            <td>${cell.key.hour} : 00</td>
                            <c:if test="${cell.value == false}">
                                <td>
                                    <form method="post" action="/BeautySaloon_war/controller?command=createMeeting">
                                        <input type="hidden" name="clientId" value="${sessionScope.accountID}">
                                        <input type="hidden" name="msId" value="${ms.id}">
                                        <input type="hidden" name="time" value="${cell.key}">
                                        <input type="submit" class="btn btn-primary" value="<fmt:message key="chooseThisTime"/>">
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${cell.value == true}">
                                <td><fmt:message key="taken"/></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>

                <c:if test="${daysFromNow > 0}">
                    <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}&scheduleDay=${dayOfWeek eq 'MONDAY' ? daysFromNow - 2 : daysFromNow - 1}"
                       class="btn btn-info" role="button"><fmt:message key="previousDay"/></a>
                </c:if>

                <c:if test="${daysFromNow < 14}">
                    <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}&scheduleDay=${dayOfWeek eq 'SATURDAY' ? daysFromNow + 2 : daysFromNow + 1}"
                       class="btn btn-info" role="button"><fmt:message key="nextDay"/></a>
                </c:if>

            </div>
        </div>
    </div>
</body>
</html>
