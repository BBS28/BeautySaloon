package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Condition;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
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
    private static final String START_COMMAND = "Command ChangeTimeSlotCommand start";
    private static final String END_COMMAND = "Command ChangeTimeSlotCommand finished";
    private static final String START_CASE_POST = "Case with Method post start";
    private static final String END_CASE_POST = "Case with Method post finished";
    private static final String START_CASE_GET = "Case with Method get start";
    private static final String END_CASE_GET = "Case with Method get finished";
    private static final String MEETING_ID = "meetingId";
    private static final String SLOT_DATETIME = "slot dateTime";
    private static final String NEW_MEETING_PARAMETERS = "newMeeting parameters - %s %s %s %s";
    private static final String SLOT_ID = "slotId";
    private static final String DELETED_TIME_SLOT = "deleted timeslot";

    /**
     * Execution method for ChangeTimeSlotCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        //inserts meeting with new time and deletes previous
        if (Constant.POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            log.debug(START_CASE_POST);
            long previousMeetingId = Long.parseLong(request.getParameter(MEETING_ID));
            log.debug(MEETING_ID + Constant.POINTER + previousMeetingId);
            Meeting previousMeeting = meetingService.getById(previousMeetingId);
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter(Constant.SLOT));
            log.debug(SLOT_DATETIME + Constant.POINTER + dateTime);
            //create meeting with new parameters
            Meeting newMeeting = new Meeting();
            newMeeting.setCondition(Condition.ACTIVE);
            newMeeting.setCatalog(previousMeeting.getCatalog());
            newMeeting.setClient(previousMeeting.getClient());
            newMeeting.setDateTime(dateTime);
            log.debug(String.format(NEW_MEETING_PARAMETERS,
                    newMeeting.getCondition(),
                    newMeeting.getCatalog(),
                    newMeeting.getClient().getName(),
                    newMeeting.getDateTime()));
            //delete old meeting
            if (meetingService.insert(newMeeting) != null) {
                meetingService.deleteById(previousMeetingId);
            }
            log.debug(END_CASE_POST);
            return Path.COMMAND_ADMIN_CABINET;

        } else {
            //create schedule
            log.debug(START_CASE_GET);
            long meetingId = Long.parseLong(request.getParameter(SLOT_ID));
            Meeting meeting = meetingService.getById(meetingId);
            List<Meeting> meetingList = meetingService.getAll();
            Master master = meeting.getCatalog().getMaster();
            long masterId = master.getId();
            meetingList.removeIf(nextMeeting -> nextMeeting.getCatalog().getMaster().getId() != masterId);
            List<LocalDateTime> emptySchedule = createEmptyFutureSchedule(Constant.MAX_DAYS_FOR_REGISTER);
            Iterator<LocalDateTime> timeIterator = emptySchedule.iterator();
            while (timeIterator.hasNext()) {
                LocalDateTime time = timeIterator.next();
                for (Meeting m : meetingList) {
                    if (time.equals(m.getDateTime())) {
                        log.debug(DELETED_TIME_SLOT + Constant.POINTER + time);
                        timeIterator.remove();
                    }
                }
            }

            request.setAttribute(Constant.SCHEDULE, emptySchedule);
            request.setAttribute(Constant.MEETING, meeting);
            log.debug(END_CASE_GET);
            log.debug(END_COMMAND);
            return Path.CHANGE_TS_PATH;
        }
    }

    /**
     * create empty time slots schedule from now
     * for a certain number of days except Sunday
     *
     * @param days
     */
    private static List<LocalDateTime> createEmptyFutureSchedule(int days) {
        List<LocalDateTime> emptySchedule = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        dateTime = dateTime.minusNanos(dateTime.getNano());
        int delta = Constant.END_WORKING_DAY_HOUR - dateTime.getHour();
        for (int i = 1; i < Constant.HOURS_PER_DAY * days + delta; i++) {
            LocalDateTime timeCell = dateTime.plusHours(i);
            if (timeCell.getHour() < Constant.END_WORKING_DAY_HOUR && timeCell.getHour() >= Constant.START_WORKING_DAY_HOUR && !timeCell.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                emptySchedule.add(timeCell);
            }
        }
        return emptySchedule;
    }
}
