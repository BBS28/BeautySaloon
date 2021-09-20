package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.ClientDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.ClientServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.CatalogServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateMeetingCommand extends Command {
    public static final String PAGE_TIME_SLOTS_LIST = "/WEB-INF/jsp/timeSlot.jsp";
    private static final long serialVersionUID = 8481491565171543283L;
    private static final Logger log = Logger.getLogger(CreateMeetingCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
        CatalogService msService = new CatalogServiceImpl(new CatalogDaoImpl());
        LocalDateTime meetingTime =  LocalDateTime.parse(request.getParameter("time"));
        log.debug(meetingTime);
        long msId = Long.parseLong(request.getParameter("msId"));
        Catalog ms = msService.getById(msId);
        long clientId = Long.parseLong(request.getParameter("clientId"));
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

        return String.format("controller?command=showTimeSlots&msId=%s", msId);
    }
}