package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
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
    private static final String START_COMMAND = "Command LeaveReviewCommand start";
    private static final String END_COMMAND = "Command LeaveReviewCommand finished";
    private static final String MEETING_ID = "meetingId";
    private static final String TEXT_REVIEW = "textReview";
    private static final String RESULT_UPDATING = "result updating";


    /**
     * Execution method for LeaveReviewCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());

        if (Constant.POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            long meetingId = Long.parseLong(request.getParameter(MEETING_ID));
            log.debug(MEETING_ID + Constant.POINTER + meetingId);
            Meeting meeting = meetingService.getById(meetingId);
            ReviewService reviewService = new ReviewServiceImpl(new ReviewDaoImpl());
            Review review = new Review();
            log.debug(Constant.REVIEW_RATE + Constant.POINTER + request.getParameter(Constant.REVIEW_RATE));
            log.debug(TEXT_REVIEW + Constant.POINTER + request.getParameter(TEXT_REVIEW));
            int rate = Integer.parseInt(request.getParameter(Constant.REVIEW_RATE));
            String textReview = request.getParameter(TEXT_REVIEW);
            review.setRate(rate);
            review.setText(textReview);
            review = reviewService.insert(review);
            meeting.setReviewId(review.getId());
            boolean result = meetingService.update(meeting);
            log.debug(RESULT_UPDATING + Constant.POINTER + result);
            return Path.COMMAND_CLIENT_CABINET;

        } else {
            long meetingId = Long.parseLong(request.getParameter(MEETING_ID));
            Meeting meeting = meetingService.getById(meetingId);
            request.setAttribute(Constant.MEETING, meeting);
            log.debug(END_COMMAND);
            return Path.REVIEW_PATH;
        }
    }
}
