package ua.kharkiv.epam.shchehlov.utils;

import java.time.LocalDateTime;

public class TimeSlot {
    private LocalDateTime localDateTime;
    private String weekDay;
    private String month;
    private int day;
    private int hour;
    boolean taken = false;

    public TimeSlot(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.weekDay = localDateTime.getDayOfWeek().toString();
        this.month = localDateTime.getMonth().toString();
        this.day = localDateTime.getDayOfMonth();
        this.hour = localDateTime.getHour();
    }


}
