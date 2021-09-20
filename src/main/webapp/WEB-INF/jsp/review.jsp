<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

    <%@ include file="/WEB-INF/jspf/navbar.jspf" %>

    <div class="container">

        <div class="row">

            <div class="col-sm-3 col-md-2">
                <p></p>
                <a href="/BeautySaloon_war/controller?command=clientCabinet"
                   class="btn btn-success" role="button"><fmt:message key="cabinet"/></a>
                <p></p>
            </div>

            <div class="col-sm-9 col-md-8">
                <h2><fmt:message key="review"/></h2>
                <h4><fmt:message key="date"/>: <a>${meeting.dateTime}</a></h4>
                <h4><fmt:message key="service"/>: <a>${meeting.catalog.service.name}</a></h4>
                <h4><fmt:message key="master"/>: <a>${meeting.catalog.master.surname} ${meeting.catalog.master.name}</a></h4>
                <p></p>

                <form class="form-horizontal" method="post" action="/BeautySaloon_war/controller?command=leaveReview">

                    <div class="form-group">
                        <label class="control-label col-xs-3" for="rate"><fmt:message key="rateVisit"/>:</label>
                        <div class="col-xs-2">
                            <select id="rate" name="rate" class="form-control">
                                <option>5</option>
                                <option>4</option>
                                <option>3</option>
                                <option>2</option>
                                <option>1</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-xs-3" for="textReview"><fmt:message key="review"/>:</label>
                        <div class="col-xs-9">
                            <textarea rows="10" class="form-control" id="textReview" name="textReview"
                                      placeholder="<fmt:message key="writeText"/>"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <input type="hidden" name="meetingId" value="${meeting.id}">
                            <input type="submit" class="btn btn-primary" value="Leave feedback">
                            <input type="reset" class="btn btn-default" value="Clear form">
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>

</body>
</html>
