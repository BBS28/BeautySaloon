<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>


    <div class="container">

        <div class="row">

            <div class="col-sm-3 col-md-2">


            </div>

            <div class="col-sm-9 col-md-8">

                <table class="table">
                    <h2><fmt:message key="ourServices"/></h2>

                    <thead>

                    <tr>
                        <th>
                            <fmt:message key="master"/>
                            <a href="/BeautySaloon_war/controller?command=showMasterServices&sort=master&sFilter=${sFilter}&mFilter=${mFilter}">
                                <span class="glyphicon glyphicon-sort-by-alphabet"></span>
                            </a>
                        </th>
                        <th>
                            <fmt:message key="masterRate"/>
                            <a href="/BeautySaloon_war/controller?command=showMasterServices&sort=rate&sFilter=${sFilter}&mFilter=${mFilter}">
                                <span class="glyphicon glyphicon-sort-by-order-alt"></span>
                            </a>
                        </th>
                        <th><fmt:message key="service"/></th>
                        <th><fmt:message key="servicePrice"/></th>

                        <c:if test="${role == 'client'}">
                            <th><fmt:message key="seeTimetable"/></th>
                        </c:if>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach items="${msList}" var="ms" varStatus="loop">

                        <tr>
                            <td>
                                  <a href="/BeautySaloon_war/controller?command=showMasterServices&mFilter=${ms.master.id}">
                                          ${ms.master.name} ${ms.master.surname}
                                  </a>
                            <td>
                                <span class="glyphicon glyphicon-star"></span>
                                <fmt:formatNumber value="${ms.master.rate}" pattern="#.#"/>
                            </td>
                            <td>
                                <a href="/BeautySaloon_war/controller?command=showMasterServices&sFilter=${ms.service.id}">
                                        ${ms.service.name}
                                </a>
                            </td>
                            <td><fmt:formatNumber value="${ms.service.price}" pattern="#.00"/></td>
                            <c:if test="${role == 'client'}">
                                <td>
                                    <a href="/BeautySaloon_war/controller?command=showTimeSlots&msId=${ms.id}"
                                       class="btn btn-primary" role="button"><fmt:message key="choose"/></a>
                                </td>
                            </c:if>
                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

                <p>
                    <a href="/BeautySaloon_war/controller?command=showMasterServices"
                       class="btn btn-default" role="button"><fmt:message key="clearFilter"/></a>
                </p>

            </div>

        </div>

    </div>
</body>
</html>
