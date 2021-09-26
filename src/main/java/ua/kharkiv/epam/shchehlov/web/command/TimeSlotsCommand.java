package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.CatalogServiceImpl;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TimeSlotsCommand extends Command {
    private static final long serialVersionUID = -8481491565171573283L;
    private static final Logger log = Logger.getLogger(TimeSlotsCommand.class);
    private static final String START_COMMAND = "Command TimeSlotsCommand start";
    private static final String END_COMMAND = "Command TimeSlotsCommand finished";
    private static final String MS_ID = "msId";
    private static final String SCHEDULE_DAY = "scheduleDay";
    private static final String DAYS_FROM_NOW = "daysFromNow";
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String CURRENT_TIME = "currentTime";

    /**
     * Execution method for TimeSlotsCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        CatalogService catalogService = new CatalogServiceImpl(new CatalogDaoImpl());
        log.debug(MS_ID + Constant.POINTER + request.getParameter(MS_ID));

        long masterServiceId = Long.parseLong(String.valueOf(request.getParameter(MS_ID)));
        Catalog catalog = catalogService.getById(masterServiceId);
        Master master = catalog.getMaster();
        log.debug(Constant.MASTER + Constant.POINTER + master);
        List<Catalog> catalogList = catalogService.getAll();
        List<Catalog> allCatalogs = new ArrayList<>();
        for (Catalog c : catalogList) {
            if (c.getMaster().getId().equals(master.getId())) {
                allCatalogs.add(c);
            }
        }

        //All meetings of current master
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetings = meetingService.getAll();
        List<Meeting> allMasterMeetings = new ArrayList<>();
        for (Meeting m : meetings) {
            log.debug(m.getId() + " " + m.getCondition());
            for (Catalog ms : allCatalogs) {
                if (m.getCatalog().getId().equals(ms.getId())) {
                    allMasterMeetings.add(m);
                }
            }
        }

        //All ACTIVE meeting's date, time
        List<LocalDateTime> masterRegistrations = new ArrayList<>();
        for (Meeting m : allMasterMeetings) {
            if (m.getCondition().equals(Condition.ACTIVE)) {
                masterRegistrations.add(m.getDateTime());
            }
        }

        int daysFromNow;
        if (request.getParameter(SCHEDULE_DAY) != null) {
            daysFromNow = Integer.parseInt(request.getParameter(SCHEDULE_DAY));
        } else {
            daysFromNow = 0;
        }

        DayOfWeek dayOfWeek = LocalDateTime.now().plusDays(daysFromNow).getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            daysFromNow++;
            dayOfWeek = DayOfWeek.MONDAY;
        }

        //Creating empty slots for current daysFromNow
        List<LocalDateTime> emptySchedule = createEmptyDaySchedule(daysFromNow);
        if (emptySchedule.isEmpty()) {
            emptySchedule = createEmptyDaySchedule(++daysFromNow);
        }

        Map<LocalDateTime, Boolean> schedule = new LinkedHashMap<>();
        for (LocalDateTime t : emptySchedule) {
            schedule.put(t, false);
        }
        for (LocalDateTime m : masterRegistrations) {
            schedule.replace(m, true);
        }

        request.setAttribute(Constant.SCHEDULE, schedule);
        request.setAttribute(Constant.MS, catalog);
        request.setAttribute(DAYS_FROM_NOW, daysFromNow);
        request.setAttribute(DAY_OF_WEEK, dayOfWeek);
        request.setAttribute(CURRENT_TIME, LocalDateTime.now());
        log.debug(END_COMMAND);
        return Path.TIME_SLOTS_LIST_PATH;
    }

    /**
     * create empty time slots schedule from moment of now
     * for a certain number of days except Sunday
     *
     * @param daysFromNow
     */
    private static List<LocalDateTime> createEmptyDaySchedule(int daysFromNow) {
        List<LocalDateTime> emptySchedule = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusHours(dateTime.getHour() - Constant.HOURS_BEFORE_WORKING_DAY_STARTS);
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        dateTime = dateTime.minusNanos(dateTime.getNano());
        int deltaWorkDay = Constant.HOURS_PER_DAY * daysFromNow;
        int deltaWorkHours = Constant.END_WORKING_DAY_HOUR - dateTime.getHour();
        for (int i = deltaWorkDay; i < deltaWorkDay + deltaWorkHours; i++) {
            LocalDateTime timeCell = dateTime.plusHours(i);
            if (timeCell.getHour() < Constant.END_WORKING_DAY_HOUR
                    && timeCell.getHour() > Constant.HOURS_BEFORE_WORKING_DAY_STARTS
                    && !timeCell.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                    && timeCell.isAfter(LocalDateTime.now())) {
                emptySchedule.add(timeCell);
            }
        }
        return emptySchedule;
    }
}
