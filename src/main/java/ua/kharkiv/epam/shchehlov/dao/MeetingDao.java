package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface MeetingDao extends BasicDao<Meeting> {
    List<Meeting> getAllByDate(LocalDate date);
}
