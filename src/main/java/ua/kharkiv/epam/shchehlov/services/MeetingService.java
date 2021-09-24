package ua.kharkiv.epam.shchehlov.services;

import ua.kharkiv.epam.shchehlov.entity.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService extends BasicService<Meeting> {
    List<Meeting> getAllByDate(LocalDate date);
}
