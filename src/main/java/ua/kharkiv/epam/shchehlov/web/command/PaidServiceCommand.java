package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class PaidServiceCommand extends Command {
    private static final long serialVersionUID = 8481341669171573292L;
    private static final Logger log = Logger.getLogger(DoneServiceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long meetingId = Long.parseLong(request.getParameter("meetingId"));
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        Meeting meeting = meetingService.getById(meetingId);
        if (meeting.getCondition().equals(Condition.PAID)) {
            meeting.setCondition(Condition.DONE);
        } else if (meeting.getCondition().equals(Condition.DONE)){
            meeting.setCondition(Condition.PAID);
        }
        boolean result = meetingService.update(meeting);
        log.debug(result);
        return "controller?command=adminCabinet";
    }
}
