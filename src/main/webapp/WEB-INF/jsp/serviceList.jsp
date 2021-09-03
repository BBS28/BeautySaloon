<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 03.09.2021
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--===========================================================================
JSTL core tag library.
===========================================================================--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--===========================================================================
JSTL functions tag library.
===========================================================================--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>

<%--===========================================================================
JSTL 1.1 i18n-capable formatting library
===========================================================================--%>

<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>

<%--<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>--%>

<%--<fmt:setLocale value="ru"/>--%>
<%--<fmt:setLocale value="${sessionScope.currentLocale}"/>--%>
<%--<fmt:setBundle basename="local"/>--%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Service List</title>
</head>
<body>
    <div class="container">
        <div>
            <table  border="1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Duration</th>
                    <th>Price</th>
                </tr>
                <c:forEach items="${serviceList2}" var="service" varStatus="loop" >
                    <tr>
                        <td>${service.id}</td>
                        <td>${service.name}</td>
                        <td>${service.duration}</td>
                        <td>${service.price}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div>
        ${sessionScope.serviceList2}
    </div>
    <div>
        ${requestScope.serviceList2}
    </div>
</body>

</html>
