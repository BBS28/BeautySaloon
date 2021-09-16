<%--
  Created by IntelliJ IDEA.
  User: bbsch
  Date: 15.09.2021
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Review</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">

        <div class="row">

            <div class="col-sm-3 col-md-2">
                <p></p>
                <a href="/BeautySaloon_war/controller?command=clientCabinet"
                   class="btn btn-success" role="button">Cabinet</a>
                <p></p>
            </div>

            <div class="col-sm-9 col-md-8">
                <h2>Review</h2>
                <h4>Date: <a>${meeting.dateTime}</a></h4>
                <h4>Service: <a>${meeting.catalog.service.name}</a></h4>
                <h4>Master: <a>${meeting.catalog.master.surname} ${meeting.catalog.master.name}</a></h4>
                <p></p>

                <form class="form-horizontal" method="post" action="/BeautySaloon_war/controller?command=leaveReview">

                    <div class="form-group">
                        <label class="control-label col-xs-3" for="rate">Rate your visit:</label>
                        <div class="col-xs-2">
                            <select id="rate" name="rate" class="form-control" >
                                <option>5</option>
                                <option>4</option>
                                <option>3</option>
                                <option>2</option>
                                <option>1</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-xs-3" for="textReview">Review:</label>
                        <div class="col-xs-9">
                            <textarea rows="10" class="form-control" id="textReview" name="textReview"
                                      placeholder="Write Your review text here..."></textarea>
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
