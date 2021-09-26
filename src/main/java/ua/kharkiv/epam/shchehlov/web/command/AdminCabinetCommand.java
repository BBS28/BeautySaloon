package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
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
import java.util.List;

public class AdminCabinetCommand extends Command {
    private static final long serialVersionUID = 2481491566371573283L;
    private static final Logger log = Logger.getLogger(AdminCabinetCommand.class);
    private static final String START_COMMAND = "Command AdminCabinetCommand starts";
    private static final String END_COMMAND = "Command AdminCabinetCommand finished";
    private static final String ACCOUNT_ID = "accountID";
    private static final String MEETING_LIST = "meetingList";
    private static final String CURRENT_TIME = "currentTime";
    private static final String MASTER_SORT = "masterSort";
    private static final String SERVICE_SORT = "serviceSort";
    private static final String DATE_SORT = "dateSort";
    private static final String CLIENT_SORT = "ClientSort";
    private static final String LIST_IS_NOT_EMPTY = "list is not empty";

    /**
     * Execution method for AdminCabinetCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        HttpSession session = request.getSession();
        long adminId = (Long) session.getAttribute(ACCOUNT_ID);
        AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
        Account admin = accountService.getById(adminId);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        int day;
        if (request.getParameter(Constant.DAY) == null) {
            day = 0;
        } else {
            day = Integer.parseInt(request.getParameter(Constant.DAY));
        }
        log.debug(Constant.DAY + Constant.POINTER + day);

        String scroll;
        if (request.getParameter(Constant.SCROLL) != null) {
            scroll = request.getParameter(Constant.SCROLL);
        } else {
            scroll = Constant.FORWARD;
        }
        List<Meeting> meetingList;
        do {
            log.debug(Constant.DAY + Constant.POINTER + day);
            log.debug(Constant.SCROLL + Constant.POINTER + scroll);
            LocalDate date = LocalDateTime.now().plusDays(day).toLocalDate();
            log.debug(Constant.DATE + Constant.POINTER + date);
            meetingList = meetingService.getAllByDate(date);
            if (!meetingList.isEmpty()) {
                log.debug(LIST_IS_NOT_EMPTY);
                break;
            }
            if (scroll.equals(Constant.FORWARD)) {
                day++;
            } else if (scroll.equals(Constant.BACK)) {
                day--;
            }
        } while (day < Constant.MAX_DAYS_FOR_REGISTER && day > Constant.MAX_DAYS_AGO);
        String sortParameter;
        //sort list of Meeting
        if (request.getParameter(Constant.SORT) != null) {
            sortParameter = request.getParameter(Constant.SORT);
            switch (sortParameter) {
                case MASTER_SORT:
                    meetingList.sort(Comparator.comparing(o -> o.getCatalog().getMaster().getName()));
                    break;
                case SERVICE_SORT:
                    meetingList.sort(Comparator.comparing(o -> o.getCatalog().getService().getName()));
                    break;
                case DATE_SORT:
                    meetingList.sort(Comparator.comparing(Meeting::getDateTime));
                    break;
                case CLIENT_SORT:
                    meetingList.sort(Comparator.comparing(o -> o.getClient().getSurname()));
                    break;
            }
        } else {
            meetingList.sort(Comparator.comparing(Meeting::getDateTime));
        }

        request.setAttribute(Constant.ADMIN, admin);
        request.setAttribute(MEETING_LIST, meetingList);
        request.setAttribute(CURRENT_TIME, LocalDateTime.now());
        request.setAttribute(Constant.DAY, day);
        log.debug(END_COMMAND);
        return Path.ADMIN_PAGE;
    }
}
