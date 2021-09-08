package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.MeetingDao;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.MeetingService;

import java.util.List;

public class MeetingServiceImpl implements MeetingService {
    private final MeetingDao meetingDao;

    public MeetingServiceImpl(MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    @Override
    public List<Meeting> getAll() {
        return meetingDao.getAll();
    }

    @Override
    public Meeting getById(Long id) {
        return meetingDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return meetingDao.deleteById(id);
    }

    @Override
    public Meeting insert(Meeting item) {
        return meetingDao.insert(item);
    }

    @Override
    public boolean update(Meeting item) {
        return meetingDao.update(item);
    }
}
