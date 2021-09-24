<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <h1>BEAUTY SALOON</h1>
                <form method="post" action="/BeautySaloon_war/controller?command=logIn">
                    <div class="form-group">
                        <label for="login"><fmt:message key="login"/>:</label><br>
                        <input type="text" name="login" id="login" class="form-control" placeholder="<fmt:message key="login"/>">
                    </div>
                    <div class="form-group">
                        <label for="password"><fmt:message key="password"/>:</label><br>
                        <input type="password" name="password" id="password" class="form-control"
                               placeholder="<fmt:message key="password"/>">
                    </div>
                    <input type="submit" class="btn btn-success" value="<fmt:message key="submit"/>">
                    <a class="text-danger">
                        ${warn}
                    </a><br><br>
                </form>


            </div>
            <div class="col-sm-4"></div>
        </div>
    </div>
</body>
</html>