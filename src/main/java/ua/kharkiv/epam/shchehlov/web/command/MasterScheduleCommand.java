package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MasterDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.MasterService;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MasterServiceImpl;
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

public class MasterScheduleCommand extends Command {
    private static final long serialVersionUID = -3281491565171573283L;
    private static final Logger log = Logger.getLogger(MasterScheduleCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        MasterService masterService = new MasterServiceImpl(new MasterDaoImpl());
        long masterId = (Long) session.getAttribute("accountID");
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
        if (request.getParameter("scheduleDay") == null) {
            daysFromNow = 0;
        } else {
            daysFromNow = Integer.parseInt(request.getParameter("scheduleDay"));
        }

        DayOfWeek dayOfWeek = null;
        List<LocalDateTime> emptyDailySchedule = createEmptyDailySchedule(daysFromNow);
        for (LocalDateTime d : emptyDailySchedule) {
            log.debug(d.getDayOfWeek());
            dayOfWeek = d.getDayOfWeek();
        }

        Map<LocalDateTime, Meeting> masterDaySchedule = new LinkedHashMap<>();
        for (LocalDateTime dateTime : emptyDailySchedule) {
            masterDaySchedule.put(dateTime, null);
        }
        for (Meeting meeting : allMasterMeetings) {
            masterDaySchedule.replace(meeting.getDateTime(), meeting);
        }

        request.setAttribute("masterDaySchedule", masterDaySchedule);
        request.setAttribute("master", master);
        request.setAttribute("daysFromNow", daysFromNow);
        request.setAttribute("dayOfWeek", dayOfWeek);

        return "/WEB-INF/jsp/masterSchedule.jsp";
    }

    private List<LocalDateTime> createEmptyDailySchedule(int daysFromNow) {
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
            if (timeCell.getHour() < 18 && timeCell.getHour() > 8 && !timeCell.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                emptySchedule.add(timeCell);
            }
        }
        return emptySchedule;
    }
}
