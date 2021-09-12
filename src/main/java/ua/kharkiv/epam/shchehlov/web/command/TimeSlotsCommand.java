package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
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
import java.util.*;

public class TimeSlotsCommand extends Command{
    public static final String PAGE_TIME_SLOTS_LIST = "/WEB-INF/jsp/timeSlot.jsp";
    private static final long serialVersionUID = -8481491565171573283L;
    private static final Logger log = Logger.getLogger(TimeSlotsCommand.class);

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
        for (Catalog c : catalogList){
            if (c.getMaster().getId().equals(master.getId())) {
                allCatalogs.add(c);
//                log.debug("added masterService" + ms.getId() + " " + ms.getMaster().getSurname() + " to allMasterServices");
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

        //Creating empty slots
        List<LocalDateTime> emptySchedule = createEmptyFutureSchedule(14);

        //
        Map<LocalDateTime, Boolean> schedule = new LinkedHashMap<>();
        for (LocalDateTime t : emptySchedule) {
            schedule.put(t, false);
        }
        for(LocalDateTime m : masterRegistrations) {
            schedule.replace(m, true);
        }

        for (Map.Entry<LocalDateTime, Boolean> entry : schedule.entrySet()){
            log.debug(entry.getKey() + " " + entry.getValue());
        }

        request.setAttribute("masterRegistrations", masterRegistrations);
        request.setAttribute("schedule", schedule);
        request.setAttribute("ms", catalog);

        return PAGE_TIME_SLOTS_LIST;
    }

    private List<LocalDateTime> createEmptyFutureSchedule (int days) {
        List<LocalDateTime> emptySchedule = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        dateTime = dateTime.minusNanos(dateTime.getNano());
        int delta = 18 - dateTime.getHour();
        for (int i = 1; i < 24 * days + delta; i++) {
            LocalDateTime timeCell = dateTime.plusHours(i);
            if (timeCell.getHour() < 18 && timeCell.getHour() > 8 && !timeCell.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                emptySchedule.add(timeCell);
            }
        }
        return emptySchedule;
    }


}
