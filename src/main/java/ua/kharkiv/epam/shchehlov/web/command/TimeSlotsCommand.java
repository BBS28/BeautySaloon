package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
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
import javax.servlet.http.HttpSession;
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

    /**
     * Execution method for TimeSlotsCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CatalogService catalogService = new CatalogServiceImpl(new CatalogDaoImpl());
        log.debug("msId= " + request.getParameter("msId"));

        long masterServiceId = Long.parseLong(String.valueOf(request.getParameter("msId")));
        Catalog catalog = catalogService.getById(masterServiceId);
        Master master = catalog.getMaster();
        log.debug("master = " + master);
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
//                    log.debug("added " + m + " to allMasterMeetings" + " master - " + m.getMasterService().getMaster().getSurname() +
//                            " condition = " + m.getCondition());
                }
            }
        }

        for (Meeting m : allMasterMeetings) {
            log.debug("allMasterMeetings, element - " + m.getCondition() + " " + m.getDateTime());
        }

        //All ACTIVE meeting's date, time
        List<LocalDateTime> masterRegistrations = new ArrayList<>();
        for (Meeting m : allMasterMeetings) {
            if (m.getCondition().equals(Condition.ACTIVE)) {
                masterRegistrations.add(m.getDateTime());
            }
        }

        for (LocalDateTime m : masterRegistrations) {
            log.debug("masterRegistrations, element - " + m);
        }

        int daysFromNow;
        if (request.getParameter("scheduleDay") != null) {
            daysFromNow = Integer.parseInt(request.getParameter("scheduleDay"));
        } else {
            daysFromNow = 0;
        }
        log.debug(String.format("daysFromNow - %s", daysFromNow));

        DayOfWeek dayOfWeek = LocalDateTime.now().plusDays(daysFromNow).getDayOfWeek();

        log.debug(String.format("dayOfWeek 1 - %s", dayOfWeek));
        if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            daysFromNow++;
            dayOfWeek = DayOfWeek.MONDAY;
        }
        log.debug(String.format("dayOfWeek 2 - %s", dayOfWeek));


        //Creating empty slots for current daysFromNow
        List<LocalDateTime> emptySchedule = createEmptyDaySchedule(daysFromNow);
        if (emptySchedule.isEmpty()) {
            emptySchedule = createEmptyDaySchedule(++daysFromNow);
        }

        //
        Map<LocalDateTime, Boolean> schedule = new LinkedHashMap<>();
        for (LocalDateTime t : emptySchedule) {
            schedule.put(t, false);
        }
        for (LocalDateTime m : masterRegistrations) {
            schedule.replace(m, true);
        }


        for (Map.Entry<LocalDateTime, Boolean> entry : schedule.entrySet()) {
            log.debug(entry.getKey() + " " + entry.getValue());
        }

//        request.setAttribute("masterRegistrations", masterRegistrations);
        HttpSession session = request.getSession();
        log.debug(String.format("role - %s - ", session.getAttribute("role")));

        request.setAttribute("schedule", schedule);
        request.setAttribute("ms", catalog);
        request.setAttribute("daysFromNow", daysFromNow);
        request.setAttribute("dayOfWeek", dayOfWeek);
        request.setAttribute("currentTime", LocalDateTime.now());
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
        dateTime = dateTime.minusHours(dateTime.getHour() - 8);
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        dateTime = dateTime.minusNanos(dateTime.getNano());
        int deltaWorkDay = 24 * daysFromNow;
        int deltaWorkHours = 18 - dateTime.getHour();
        for (int i = deltaWorkDay; i < deltaWorkDay + deltaWorkHours; i++) {
            LocalDateTime timeCell = dateTime.plusHours(i);
            if (timeCell.getHour() < 18
                    && timeCell.getHour() > 8
                    && !timeCell.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                    && timeCell.isAfter(LocalDateTime.now())) {
                emptySchedule.add(timeCell);
            }
        }
        return emptySchedule;
    }
}
