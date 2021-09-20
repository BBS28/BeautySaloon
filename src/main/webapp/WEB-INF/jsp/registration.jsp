<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <h3><fmt:message key="registerNew"/></h3>

                <form method="post" action="/BeautySaloon_war/controller?command=registration">
                    <div class="form-group">
                        <label for="login"><fmt:message key="login"/>*:</label><br>
                        <input type="text" name="login" id="login" class="form-control" placeholder="<fmt:message key="login"/>">
                    </div>

                    <div class="form-group">
                        <label for="password"><fmt:message key="password"/>*:</label><br>
                        <input type="password" name="password" id="password" class="form-control"
                               placeholder="<fmt:message key="password"/>">
                    </div>

                    <div class="form-group">
                        <label for="repeatPassword"><fmt:message key="repeatPassword"/>*:</label><br>
                        <input type="password" name="repeatPassword" id="repeatPassword" class="form-control"
                               placeholder="<fmt:message key="repeatPassword"/>">
                    </div>

                    <div class="form-group">
                        <label for="name"><fmt:message key="name"/>*:</label><br>
                        <input type="text" name="name" id="name" class="form-control" placeholder="<fmt:message key="name"/>">
                    </div>

                    <div class="form-group">
                        <label for="surname"><fmt:message key="surname"/>*:</label><br>
                        <input type="text" name="surname" id="surname" class="form-control" placeholder="<fmt:message key="surname"/>">
                    </div>

                    <div class="form-group">
                        <label for="email">Email*:</label><br>
                        <input type="email" name="email" id="email" class="form-control" placeholder="user@mail.com">
                    </div>

                    <div class="text-danger">
                        ${warn}
                    </div>

                    <input type="submit" class="btn btn-success" value="<fmt:message key="submit"/>"><br><br>
                </form>

            </div>
            <div class="col-sm-4"></div>
        </div>
    </div>
</body>
</html>
