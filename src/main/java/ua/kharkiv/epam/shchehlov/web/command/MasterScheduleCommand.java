package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MasterDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.MasterService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.CatalogServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MasterServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MasterScheduleCommand extends Command {
    private static final long serialVersionUID = -3281491565171573283L;
    private static final Logger log = Logger.getLogger(MasterScheduleCommand.class);
    private static final String START_COMMAND = "MasterScheduleCommand starts";
    private static final String END_COMMAND = "MasterScheduleCommand finished";
    private static final String ACCOUNT_ID = "accountID";
    private static final String SCHEDULE_DAY = "scheduleDay";
    private static final String MASTER_DAY_SCHEDULE = "masterDaySchedule";
    private static final String DAYS_FROM_NOW = "daysFromNow";
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String CURRENT_TIME = "currentTime";

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
        HttpSession session = request.getSession();
        MasterService masterService = new MasterServiceImpl(new MasterDaoImpl());
        long masterId = (Long) session.getAttribute(ACCOUNT_ID);
        Master master = masterService.getById(masterId);

        CatalogService catalogService = new CatalogServiceImpl(new CatalogDaoImpl());
        List<Catalog> catalogList = catalogService.getAll();
        List<Catalog> allMasterCatalogs = new ArrayList<>();
        for (Catalog catalog : catalogList) {
            log.debug(catalog.getMaster().getId());
            if (catalog.getMaster().getId().equals(master.getId())) {
                allMasterCatalogs.add(catalog);
            }
        }

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetings = meetingService.getAll();
        List<Meeting> allMasterMeetings = new ArrayList<>();
        for (Meeting m : meetings) {
            log.debug(m.getId() + " " + m.getCondition());
            for (Catalog ms : allMasterCatalogs) {
                if (m.getCatalog().getId().equals(ms.getId())) {
                    allMasterMeetings.add(m);
                }
            }
        }

        int daysFromNow;
        if (request.getParameter(SCHEDULE_DAY) == null) {
            daysFromNow = 0;
        } else {
            daysFromNow = Integer.parseInt(request.getParameter(SCHEDULE_DAY));
        }
        log.debug(daysFromNow);
        DayOfWeek dayOfWeek = null;

        List<LocalDateTime> emptyDailySchedule = createEmptyDailySchedule(daysFromNow);
        for (LocalDateTime d : emptyDailySchedule) {
            log.debug(d.getDayOfWeek());
            dayOfWeek = d.getDayOfWeek();
        }

        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            daysFromNow++;
            emptyDailySchedule = createEmptyDailySchedule(daysFromNow);
            dayOfWeek = DayOfWeek.MONDAY;
        }

        Map<LocalDateTime, Meeting> masterDaySchedule = new LinkedHashMap<>();
        for (LocalDateTime dateTime : emptyDailySchedule) {
            masterDaySchedule.put(dateTime, null);
        }
        for (Meeting meeting : allMasterMeetings) {
            masterDaySchedule.replace(meeting.getDateTime(), meeting);
        }

        request.setAttribute(MASTER_DAY_SCHEDULE, masterDaySchedule);
        request.setAttribute(Constant.MASTER, master);
        request.setAttribute(DAYS_FROM_NOW, daysFromNow);
        request.setAttribute(DAY_OF_WEEK, dayOfWeek);
        request.setAttribute(CURRENT_TIME, LocalDateTime.now());
        log.debug(END_COMMAND);
        return Path.MASTER_SCHEDULE_PATH;
    }

    /**
     * create empty time slots schedule for a day
     * certain number of days from now
     *
     * @param daysFromNow
     */
    private List<LocalDateTime> createEmptyDailySchedule(int daysFromNow) {
        List<LocalDateTime> emptySchedule = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();
        log.debug(dateTime);
        dateTime = dateTime.minusHours(dateTime.getHour() - Constant.HOURS_BEFORE_WORKING_DAY_STARTS);
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        dateTime = dateTime.minusNanos(dateTime.getNano());
        log.debug(dateTime);
        int deltaWorkDay = Constant.HOURS_PER_DAY * daysFromNow;
        log.debug(deltaWorkDay);
        int deltaWorkHours = Constant.END_WORKING_DAY_HOUR - dateTime.getHour();
        log.debug(deltaWorkHours);
        for (int i = deltaWorkDay; i < deltaWorkDay + deltaWorkHours; i++) {
            LocalDateTime timeCell = dateTime.plusHours(i);
            if (timeCell.getHour() < Constant.END_WORKING_DAY_HOUR && timeCell.getHour() >= Constant.START_WORKING_DAY_HOUR) {
                emptySchedule.add(timeCell);
            }
        }
        log.debug(emptySchedule);
        return emptySchedule;
    }
}
