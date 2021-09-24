package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Path;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdminCabinetCommand extends Command {
    private static final long serialVersionUID = 2481491566371573283L;
    private static final Logger log = Logger.getLogger(AdminCabinetCommand.class);

    /**
     * Execution method for AdminCabinetCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command  AdminCabinetCommand starts");
        HttpSession session = request.getSession();
        long adminId = (Long) session.getAttribute("accountID");
        AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
        Account admin = accountService.getById(adminId);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        int day;
        if (request.getParameter("day") == null) {
            day = 0;
        } else {
            day = Integer.parseInt(request.getParameter("day"));
        }
        log.debug(String.format("day = %s", day));

        String scroll;
        if (request.getParameter("scroll") != null) {
            scroll = request.getParameter("scroll");
        } else {
            scroll = "forward";
        }
        List<Meeting> meetingList;
        do {
            log.debug(String.format("day ==> %s", day));
            log.debug(String.format("scroll ==> %s", scroll));
            LocalDate date = LocalDateTime.now().plusDays(day).toLocalDate();
            log.debug(String.format("date ==> %s", date));
            meetingList = meetingService.getAllByDate(date);
            if (!meetingList.isEmpty()) {
                log.debug("list is not empty");
                break;
            }
            if (scroll.equals("forward")) {
                day++;
            } else if (scroll.equals("back")) {
                day--;
            }
        } while (day < 14 && day > -60);
//        List<Meeting> meetingList = meetingService.getAll();
        String sortParameter;
        //sort list of Meeting
        if (request.getParameter("sort") != null) {
            sortParameter = request.getParameter("sort");
            switch (sortParameter) {
                case "masterSort":
                    meetingList.sort(Comparator.comparing(o -> o.getCatalog().getMaster().getName()));
                    break;
                case "serviceSort":
                    meetingList.sort(Comparator.comparing(o -> o.getCatalog().getService().getName()));
                    break;
                case "dateSort":
                    meetingList.sort(Comparator.comparing(Meeting::getDateTime));
                    break;
                case "ClientSort":
                    meetingList.sort(Comparator.comparing(o -> o.getClient().getSurname()));
                    break;
            }
        } else {
            meetingList.sort(Comparator.comparing(Meeting::getDateTime));
        }

        request.setAttribute("admin", admin);
        request.setAttribute("meetingList", meetingList);
        request.setAttribute("currentTime", LocalDateTime.now());
        request.setAttribute("day", day);

        log.debug("Command  AdminCabinetCommand finished");
        return Path.ADMIN_PAGE;
    }
}
