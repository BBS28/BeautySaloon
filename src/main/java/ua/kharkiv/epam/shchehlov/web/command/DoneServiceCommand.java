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

public class DoneServiceCommand extends Command {
    private static final long serialVersionUID = 8481491669171573292L;
    private static final Logger log = Logger.getLogger(DoneServiceCommand.class);

    /**
     * Execution method for DoneServiceCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command DoneServiceCommand start");
        int daysFromNow = Integer.parseInt(request.getParameter("daysFromNow"));
        long meetingId = Long.parseLong(request.getParameter("meetingId"));
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        Meeting meeting = meetingService.getById(meetingId);
        if (meeting.getCondition().equals(Condition.ACTIVE)) {
            meeting.setCondition(Condition.DONE);
        } else if (meeting.getCondition().equals(Condition.DONE)){
            meeting.setCondition(Condition.ACTIVE);
        }
        boolean result = meetingService.update(meeting);
        log.debug(result);
        if("POST".equalsIgnoreCase(request.getMethod())){
            request.setAttribute("requestTypePost", "post");
        }
        log.debug("Command DoneServiceCommand finished");
        return String.format("controller?command=masterSchedule&scheduleDay=%s", daysFromNow);
    }
}
