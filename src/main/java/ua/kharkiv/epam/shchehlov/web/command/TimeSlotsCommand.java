package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MasterServiceDaoImpl;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.entity.MasterService;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.MasterServiceService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MasterServiceServiceImpl;
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
        MasterServiceService msService = new MasterServiceServiceImpl(new MasterServiceDaoImpl());
        log.debug("msId= " + request.getParameter("msId"));

        long masterServiceId = Long.parseLong(String.valueOf(request.getParameter("msId")));
        MasterService masterService = msService.getById(masterServiceId);
        Master master = masterService.getMaster();
        log.debug("master = " + master);
        List<MasterService> msList = msService.getAll();
        List<MasterService> allMasterServices = new ArrayList<>();
        for (MasterService ms : msList){
            if (ms.getMaster().getId().equals(master.getId())) {
                allMasterServices.add(ms);
//                log.debug("added masterService" + ms.getId() + " " + ms.getMaster().getSurname() + " to allMasterServices");
            }
        }

        //All meetings of current master
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetings = meetingService.getAll();
        List<Meeting> allMasterMeetings = new ArrayList<>();
        for (Meeting m : meetings) {
            log.debug(m.getId() + " " + m.getCondition());
            for (MasterService ms : allMasterServices) {
                if (m.getMasterService().getId().equals(ms.getId())) {
                    allMasterMeetings.add(m);
//                    log.debug("added " + m + " to allMasterMeetings" + " master - " + m.getMasterService().getMaster().getSurname() +
//                            " condition = " + m.getCondition());
                }
            }
        }

        for (Meeting m : allMasterMeetings) {
            log.debug("allMasterMeetings, element - " + m.getCondition() + " " + m.getDateTime());
        }

        //All meeting's date, time & durations of services
        List<LocalDateTime> masterRegistrations = new ArrayList<>();
        for (Meeting m : allMasterMeetings) {
//            log.debug("Meeeting iteration: " + m.getCondition() + " " + m.getId());
            if (m.getCondition().equals(Condition.ACTIVE)) {
                masterRegistrations.add(m.getDateTime());
//                log.debug("masterRegistrations key = " + m.getDateTime() + " masterRegistrations value = " + m.getMasterService().getService().getDuration());
            }
        }

        for (LocalDateTime m : masterRegistrations) {
            log.debug("masterRegistrations, element - " + m);
        }

        //Schedule
        List<LocalDateTime> emptySchedule = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        dateTime = dateTime.minusNanos(dateTime.getNano());
        int delta = 18 - dateTime.getHour();
        for (int i = 1; i < 24 * 14 + delta; i++) {
            LocalDateTime timeCell = dateTime.plusHours(i);
            if (timeCell.getHour() < 18 && timeCell.getHour() > 8 && !timeCell.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                emptySchedule.add(timeCell);
            }
        }

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
        request.setAttribute("ms", masterService);

        return PAGE_TIME_SLOTS_LIST;
    }
}
