<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">

        <div class="row">

            <div class="col-sm-3 col-md-2">

            </div>

            <div class="col-sm-9 col-md-8">

                <table class="table">
                    <h2><fmt:message key="reviewsClients"/></h2>
                    <tr>
                        <th><fmt:message key="client"/></th>
                        <th><fmt:message key="review"/></th>
                    </tr>

                    <c:forEach items="${reviewMap}" var="cell" varStatus="loop">
                        <tr>
                            <td>${cell.key.client.name}</td>
                            <td>${cell.value.text}</td>
                        </tr>
                    </c:forEach>
                </table>

            </div>

        </div>

    </div>

</body>
</html>
