package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.ClientDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.CatalogServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.ClientServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateMeetingCommand extends Command {
    private static final long serialVersionUID = 8481491565171543283L;
    private static final Logger log = Logger.getLogger(CreateMeetingCommand.class);
    private static final String START_COMMAND = "Command CreateMeetingCommand start";
    private static final String END_COMMAND = "Command CreateMeetingCommand finished";
    private static final String PARAMETER_MS_ID = "&msId=";
    private static final String TIME = "time";
    private static final String MS_ID = "msId";
    private static final String CLIENT_ID = "clientId";

    /**
     * Execution method for CreateMeetingCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
        CatalogService msService = new CatalogServiceImpl(new CatalogDaoImpl());
        LocalDateTime meetingTime = LocalDateTime.parse(request.getParameter(TIME));
        log.debug(meetingTime);
        long msId = Long.parseLong(request.getParameter(MS_ID));
        Catalog ms = msService.getById(msId);
        long clientId = Long.parseLong(request.getParameter(CLIENT_ID));
        Client client = clientService.getById(clientId);

        Meeting meeting = new Meeting();
        meeting.setDateTime(meetingTime);
        meeting.setCatalog(ms);
        meeting.setClient(client);
        meeting.setCondition(Condition.ACTIVE);

        log.debug(meeting.getClient().getName() + " " +
                meeting.getCatalog().getMaster().getSurname() + " " +
                meeting.getCatalog().getService().getName() + " " +
                meeting.getDateTime() + " " +
                meeting.getCondition()
        );

        log.debug(meetingService.insert(meeting));
        log.debug(END_COMMAND);
        return Path.COMMAND_TIME_SLOTS + PARAMETER_MS_ID + msId;
    }
}