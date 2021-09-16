package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelMeetingCommand extends Command {
    private static final long serialVersionUID = 8481491566371573283L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        long meetingId = Long.parseLong(request.getParameter("meetingId"));
        boolean result = meetingService.deleteById(meetingId);
        log.debug(String.format("meeting id=%s deleted - %s", meetingId, result));

        return "controller?command=adminCabinet";

    }
}