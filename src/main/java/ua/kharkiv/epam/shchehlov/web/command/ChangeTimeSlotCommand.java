package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.*;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChangeTimeSlotCommand extends Command {
    private static final long serialVersionUID = 8481493066371573283L;
    private static final Logger log = Logger.getLogger(ChangeTimeSlotCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            long previousMeetingId = Long.parseLong(request.getParameter("meetingId"));
            log.debug(String.format("meetingId - %s", previousMeetingId));
            Meeting previousMeeting = meetingService.getById(previousMeetingId);
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("slot"));
            log.debug(String.format("slot dateTime - %s", dateTime));

            Meeting newMeeting = new Meeting();
            newMeeting.setCondition(Condition.ACTIVE);
            newMeeting.setCatalog(previousMeeting.getCatalog());
            newMeeting.setClient(previousMeeting.getClient());
            newMeeting.setDateTime(dateTime);
            log.debug(String.format("newMeeting parameters - %s %s %s %s",
                    newMeeting.getCondition(),
                    newMeeting.getCatalog(),
                    newMeeting.getClient().getName(),
                    newMeeting.getDateTime()));

            if (meetingService.insert(newMeeting) != null) {
                meetingService.deleteById(previousMeetingId);
            }

            //Todo: throw new exception
            return "controller?command=adminCabinet";

        } else {

            long meetingId = Long.parseLong(request.getParameter("slotId"));
            Meeting meeting = meetingService.getById(meetingId);
            List<Meeting> meetingList = meetingService.getAll();

            Master master = meeting.getCatalog().getMaster();
            Catalog catalog = meeting.getCatalog();
            long masterId = master.getId();

            meetingList.removeIf(nextMeeting -> nextMeeting.getCatalog().getMaster().getId() != masterId);
            List<LocalDateTime> emptySchedule = createEmptyFutureSchedule(14);
            Iterator<LocalDateTime> timeIterator = emptySchedule.iterator();

            while (timeIterator.hasNext()) {
                LocalDateTime time = timeIterator.next();
                for (Meeting m : meetingList) {
                    if (time.equals(m.getDateTime())) {
                        log.debug(String.format("deleted timeslot - %s", time));
                        timeIterator.remove();
                    }
                }
            }


            request.setAttribute("schedule", emptySchedule);
            request.setAttribute("meeting", meeting);
            return "/WEB-INF/jsp/changeTS.jsp";
        }

    }

    static List<LocalDateTime> createEmptyFutureSchedule(int days) {
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
