package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.ClientDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MasterServiceDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.MasterService;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.MasterServiceService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.ClientServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MasterServiceServiceImpl;
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
        MasterServiceService msService = new MasterServiceServiceImpl(new MasterServiceDaoImpl());
        LocalDateTime meetingTime =  LocalDateTime.parse(request.getParameter("time"));
        log.debug(meetingTime);
        long msId = Long.parseLong(request.getParameter("msId"));
        MasterService ms = msService.getById(msId);
        long clientId = Long.parseLong(request.getParameter("clientId"));
        Client client = clientService.getById(clientId);

        Meeting meeting = new Meeting();
        meeting.setDateTime(meetingTime);
        meeting.setMasterService(ms);
        meeting.setClient(client);
        meeting.setCondition(Condition.ACTIVE);

        log.debug(meeting.getClient().getName() + " " +
                meeting.getMasterService().getMaster().getSurname() + " " +
                meeting.getMasterService().getService().getName() + " " +
                meeting.getDateTime() + " " +
                meeting.getCondition()
        );

        log.debug(meetingService.insert(meeting));



        return PAGE_TIME_SLOTS_LIST;
    }
}
