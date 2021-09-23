<%@ taglib prefix="dt" uri="/WEB-INF/tld/dateTime.tld" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">
        <h2><fmt:message key="adminCabinet"/></h2>
        <div class="row">

            <div class="col-sm-3 col-md-2">
                <p><b><fmt:message key="login"/>:</b> ${admin.login}</p>
                <p><b><fmt:message key="name"/>:</b> ${admin.name}</p>
                <p><b><fmt:message key="surname"/>:</b> ${admin.surname}</p>
                <p><b>Email:</b> ${admin.email}</p>
            </div>

            <div class="col-sm-9 col-md-8">
                <h3><fmt:message key="allServices"/></h3>
                <table class="table">
                    <tr>
                        <th><fmt:message key="date"/>
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=dateSort">
                                <span class="glyphicon glyphicon-sort-by-order"></span>
                            </a>
                        </th>
                        <th></th>
                        <th><fmt:message key="master"/>
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=masterSort">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th><fmt:message key="service"/>
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=serviceSort">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th><fmt:message key="client"/>
                            <a href="/BeautySaloon_war/controller?command=adminCabinet&sort=clientSort">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th><fmt:message key="status"/></th>
                        <th></th>

                    </tr>
                    <c:forEach items="${meetingList}" var="cell" varStatus="loop">
                        <tr>
                            <td><dt:formatDT>${cell.dateTime}</dt:formatDT></td>
                            <td>
                                <c:if test="${cell.dateTime > currentTime}">
                                    <a href="/BeautySaloon_war/controller?command=changeTimeSlot&slotId=${cell.id}"
                                       class="btn btn-info" role="button"><fmt:message key="change"/></a>
                                </c:if>
                            </td>
                            <td>${cell.catalog.master.name} ${cell.catalog.master.surname}</td>
                            <td><fmt:message key="${cell.catalog.service.name}"/></td>
                            <td>${cell.client.name} ${cell.client.surname}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${cell.condition eq 'DONE' and cell.dateTime < currentTime}">
                                        <form method="post" action="/BeautySaloon_war/controller?command=paidDone">
                                            <input type="hidden" name="meetingId" value="${cell.id}">
                                            <input type="hidden" name="pageDate" value="${cell.dateTime}">
                                            <input type="submit" class="btn btn-warning" value="<fmt:message key="${cell.condition}"/>">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${cell.condition eq 'PAID' and cell.dateTime < currentTime}">
                                                <form method="post"
                                                      action="/BeautySaloon_war/controller?command=paidDone">
                                                    <input type="hidden" name="meetingId" value="${cell.id}">
                                                    <input type="hidden" name="pageDate" value="${cell.dateTime}">
                                                    <input type="submit" class="btn btn-success"
                                                           value="<fmt:message key="${cell.condition}"/>">
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="${cell.condition}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:if test="${cell.condition eq 'ACTIVE'}">
                                    <form method="post" action="/BeautySaloon_war/controller?command=cancelMeeting">
                                        <input type="hidden" name="meetingId" value="${cell.id}">
                                        <input type="hidden" name="pageDate" value="${cell.dateTime}">
                                        <input type="submit" class="btn btn-danger" value="<fmt:message key="cancel"/>">
                                    </form>
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
