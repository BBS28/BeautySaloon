package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;
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
    private static final String START_COMMAND = "Command DoneServiceCommand start";
    private static final String END_COMMAND = "Command DoneServiceCommand finished";
    private static final String DAYS_FROM_NOW = "daysFromNow";
    private static final String MEETING_ID = "meetingId";
    private static final String PARAMETER_SCHEDULE_DAY = "&scheduleDay=";

    /**
     * Execution method for DoneServiceCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        int daysFromNow = Integer.parseInt(request.getParameter(DAYS_FROM_NOW));
        long meetingId = Long.parseLong(request.getParameter(MEETING_ID));
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        Meeting meeting = meetingService.getById(meetingId);
        if (meeting.getCondition().equals(Condition.ACTIVE)) {
            meeting.setCondition(Condition.DONE);
        } else if (meeting.getCondition().equals(Condition.DONE)) {
            meeting.setCondition(Condition.ACTIVE);
        }
        boolean result = meetingService.update(meeting);
        log.debug(result);
        if (Constant.POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            request.setAttribute(Constant.REQUEST_TYPE_POST, Constant.POST);
        }
        log.debug(END_COMMAND);
        return Path.COMMAND_MASTER_SCHEDULE + PARAMETER_SCHEDULE_DAY + daysFromNow;
    }
}
