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

public class PaidServiceCommand extends Command {
    private static final long serialVersionUID = 8481341669171573292L;
    private static final Logger log = Logger.getLogger(DoneServiceCommand.class);
    private static final String START_COMMAND = "Command PaidServiceCommand start";
    private static final String END_COMMAND = "Command PaidServiceCommand finished";
    private static final String PARAMETER_DAY = "&day=";
    private static final String MEETING_ID = "meetingId";


    /**
     * Execution method for MasterScheduleCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        long meetingId = Long.parseLong(request.getParameter(MEETING_ID));
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        Meeting meeting = meetingService.getById(meetingId);
        if (meeting.getCondition().equals(Condition.PAID)) {
            meeting.setCondition(Condition.DONE);
        } else if (meeting.getCondition().equals(Condition.DONE)) {
            meeting.setCondition(Condition.PAID);
        }
        boolean result = meetingService.update(meeting);
        int day = Integer.parseInt(request.getParameter(Constant.DAY));
        if (Constant.POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            request.setAttribute(Constant.REQUEST_TYPE_POST, Constant.POST);
        }
        log.debug(result);
        log.debug(END_COMMAND);
        return Path.COMMAND_SLASH_ADMIN_CABINET + PARAMETER_DAY + day;
    }
}
