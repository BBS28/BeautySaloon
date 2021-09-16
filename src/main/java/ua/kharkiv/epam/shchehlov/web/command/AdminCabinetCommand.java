package ua.kharkiv.epam.shchehlov.web.command;

import ua.kharkiv.epam.shchehlov.dao.impl.AccountDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Account;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.AccountService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.AccountServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class AdminCabinetCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        long adminId = (Long) session.getAttribute("accountID");
        AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
        Account admin = accountService.getById(adminId);

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetingList = meetingService.getAll();

        String sortParameter;

        if (request.getParameter("sort") != null) {
            sortParameter = request.getParameter("sort");
            switch (sortParameter) {
                case "masterSort":
                    meetingList.sort(Comparator.comparing(o -> o.getCatalog().getMaster().getName()));
                    break;
                case "serviceSort":
                    meetingList.sort(Comparator.comparing(o-> o.getCatalog().getService().getName()));
                    break;
                case "dateSort":
                    meetingList.sort(Comparator.comparing(Meeting::getDateTime));
                    break;
                case "ClientSort":
                    meetingList.sort(Comparator.comparing(o-> o.getClient().getSurname()));
                    break;
            }
        }else {
            meetingList.sort(Comparator.comparing(Meeting::getDateTime));
        }


        request.setAttribute("admin", admin);
        request.setAttribute("meetingList", meetingList);
        request.setAttribute("currentTime", LocalDateTime.now());

        return "/WEB-INF/jsp/adminPage.jsp";
    }
}
