package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelMeetingCommand extends Command {
    private static final long serialVersionUID = 8481491566371573283L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private static final String START_COMMAND = "Command  CancelMeetingCommand starts";
    private static final String END_COMMAND = "Command CancelMeetingCommand finished";
    private static final String MEETING_ID = "meetingId";
    private static final String MEETING_DELETE_RESULT = "meeting id=%s deleted - %s";

    /**
     * Execution method for CancelMeetingCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        long meetingId = Long.parseLong(request.getParameter(MEETING_ID));
        boolean result = meetingService.deleteById(meetingId);
        log.debug(String.format(MEETING_DELETE_RESULT, meetingId, result));
        log.debug((END_COMMAND));
        return Path.COMMAND_ADMIN_CABINET;
    }
}
