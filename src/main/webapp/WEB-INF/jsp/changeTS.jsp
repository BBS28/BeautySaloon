<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">

        <div class="col-sm-3 col-md-2">
            <h4><fmt:message key="info"/>:</h4>
            <p><b><fmt:message key="master"/>:</b> ${meeting.catalog.master.surname} ${meeting.catalog.master.name}</p>
            <p><b><fmt:message key="service"/>:</b> ${meeting.catalog.service.name}</p>
            <p></p>
            <a href="/BeautySaloon_war/controller?command=adminCabinet" role="button"
               class="btn btn-danger"><fmt:message key="cancel"/></a>
        </div>

        <div class="col-sm-9 col-md-8">
            <h2><fmt:message key="freeSlots"/></h2>

            <table class="table">
                <tr>
                    <th><fmt:message key="weekDay"/></th>
                    <th><fmt:message key="date"/></th>
                    <th><fmt:message key="time"/></th>
                    <th><fmt:message key="register"/></th>
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
