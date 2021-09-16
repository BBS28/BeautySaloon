package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.ClientDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.ClientServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientCabinetCommand extends Command {
    private static final long serialVersionUID = 8481491616371573283L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
        long clientId = (Long) session.getAttribute("accountID");
        Client client = clientService.getById(clientId);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetingList = meetingService.getAll();

        List<Meeting> clientMeetingList = new ArrayList<>();
        for (Meeting m : meetingList) {
            if(m.getClient().getId().equals(client.getId())) {
                clientMeetingList.add(m);
            }
        }

        clientMeetingList.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));

        request.setAttribute("client", client);
        request.setAttribute("clientMeetings", clientMeetingList);

        return "/WEB-INF/jsp/clientPage.jsp";
    }
}
