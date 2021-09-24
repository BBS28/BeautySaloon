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
import java.util.*;

public class ShowReviewsCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowMasterServiceCommand.class);
    private static final long serialVersionUID = -8481215465177573283L;

    /**
     * Execution method for ShowReviewsCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command ShowReviewsCommand start");
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetingList = meetingService.getAll();
        meetingList.removeIf(meeting -> meeting.getReviewId() == 0);
        meetingList.sort(((o1, o2) -> Long.compare(o2.getReviewId(), o1.getReviewId())));
        long reviewId;
        ReviewService reviewService = new ReviewServiceImpl(new ReviewDaoImpl());
        Map<Meeting, Review> reviewMap = new LinkedHashMap<>();
        for (Meeting meeting : meetingList) {
            reviewId = meeting.getReviewId();
            reviewMap.put(meeting, reviewService.getById(reviewId));
            log.debug(reviewService.getById(reviewId).getText());
        }

        request.setAttribute("reviewMap", reviewMap);
        log.debug("Command ShowReviewsCommand finished");
        return Path.SHOW_REVIEW_PATH;
    }
}
