package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.ReviewDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.entity.Review;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.ReviewService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.ReviewServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeaveReviewCommand extends Command {
    private static final long serialVersionUID = 8481491669139873383L;
    private static final Logger log = Logger.getLogger(LeaveReviewCommand.class);

    /**
     * Execution method for LeaveReviewCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command LeaveReviewCommand start");
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            long meetingId = Long.parseLong(request.getParameter("meetingId"));
            log.debug(String.format("meetingId - %s", meetingId));
            Meeting meeting = meetingService.getById(meetingId);
            ReviewService reviewService = new ReviewServiceImpl(new ReviewDaoImpl());
            Review review = new Review();
            log.debug(String.format("rate - %s", request.getParameter("rate")));
            log.debug(String.format("textReview - %s", request.getParameter("textReview")));
            int rate = Integer.parseInt(request.getParameter("rate"));
            String textReview = request.getParameter("textReview");


            review.setRate(rate);
            review.setText(textReview);

            review = reviewService.insert(review);
            meeting.setReviewId(review.getId());
            boolean result = meetingService.update(meeting);
            log.debug(String.format("result updating - %s", result));

            return "controller?command=clientCabinet";

        }else {
            long meetingId = Long.parseLong(request.getParameter("meetingId"));
            Meeting meeting = meetingService.getById(meetingId);
            request.setAttribute("meeting", meeting);
            log.debug("Command LeaveReviewCommand finished");
            return Path.REVIEW_PATH;
        }

    }
}
