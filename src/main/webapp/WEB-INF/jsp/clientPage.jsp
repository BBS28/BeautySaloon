<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">
        <h2><fmt:message key="clientsCabinet"/></h2>
        <div class="row">
            <div class="col-sm-3 col-md-2">
                <p><b><fmt:message key="login"/>:</b> ${client.login}</p>
                <p><b><fmt:message key="name"/>:</b> ${client.name}</p>
                <p><b><fmt:message key="surname"/>:</b> ${client.surname}</p>
                <p><b>Email:</b> ${client.email}</p>

            </div>
            <div class="col-sm-9 col-md-8">
                <h3><fmt:message key="yourVisits"/></h3>
                <table class="table">
                    <tr>
                        <th><fmt:message key="weekDay"/></th>
                        <th><fmt:message key="date"/></th>
                        <th><fmt:message key="time"/></th>
                        <th><fmt:message key="service"/></th>
                        <th><fmt:message key="master"/></th>
                        <th><fmt:message key="status"/></th>
                        <th></th>
                    </tr>
                    <c:forEach items="${clientMeetings}" var="cell" varStatus="loop">
                        <tr>
                            <td><fmt:message key="${cell.dateTime.dayOfWeek}"/></td>
                            <td nowrap><fmt:message key="${cell.dateTime.month}"/>, ${cell.dateTime.dayOfMonth}</td>
                            <td>${cell.dateTime.hour} : 00</td>
                            <td><fmt:message key="${cell.catalog.service.name}"/></td>
                            <td>${cell.catalog.master.name} ${cell.catalog.master.surname}</td>
                            <td><fmt:message key="${cell.condition}"/></td>
                            <td>
                                <c:if test="${cell.reviewId == 0 and (cell.condition eq 'DONE' or cell.condition eq 'PAID')}">
                                    <a href="/BeautySaloon_war/controller?command=leaveReview&meetingId=${cell.id}"
                                       class="btn btn-primary" role="button">
                                        <fmt:message key="leaveFeedback"/>
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
