package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.MeetingDao;
import ua.kharkiv.epam.shchehlov.dao.db.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.*;

import java.sql.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MeetingDaoImpl implements MeetingDao {
    private static final Logger log = Logger.getLogger(MeetingDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_MEETINGS = "SELECT * FROM meeting";
    private static final String FIND_MEETINGS_BY_ID = "SELECT * FROM meeting WHERE id = ?";
    private static final String DELETE_MEETING_BY_ID = "DELETE FROM meeting WHERE id = ?";
    private static final String INSERT_NEW_MEETING =
            "INSERT INTO meeting " +
                    "(meeting.condition, meeting.account_id, meeting.master_service_id, meeting.meet_time) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_MEETING_BY_ID =
            "UPDATE service " +
                    "SET service.condition = ?, account_id = ?, review_id = ?, master_service_id = ?, meet_time = ?" +
                    " WHERE id = ?";


    @Override
    public List<Meeting> getAll() {
        List<Meeting> meetingList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_MEETINGS);
            Meeting meeting;
            while (rs.next()) {
                meeting = extractMeeting(rs);
                meetingList.add(meeting);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return meetingList;
    }

    @Override
    public Meeting getById(Long meetingId) {
        Meeting meeting = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_MEETINGS_BY_ID);
            ps.setLong(1, meetingId);
            rs = ps.executeQuery();
            while (rs.next()) {
                meeting = extractMeeting(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return meeting;
    }

    @Override
    public boolean deleteById(Long meetingId) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DELETE_MEETING_BY_ID);
            ps.setLong(1, meetingId);
            if (ps.executeUpdate() > 0) {
                result = true;
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error("cannot obtain categories", ex);
            }
        } finally {
            dbManager.close(con, ps);
        }
        return result;
    }

    @Override
    public Meeting insert(Meeting meeting) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(INSERT_NEW_MEETING, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, meeting.getCondition().toString());
            ps.setLong(2, meeting.getClient().getId());
            ps.setLong(3, meeting.getCatalog().getId());
            ps.setTimestamp(4, Timestamp.valueOf(meeting.getDateTime()
                    .plusHours(3)
                    .atZone(ZoneId.of("UTC"))
                    .format(DateTimeFormatter
                            .ofPattern("uuuu-MM-dd HH:mm:ss"))));
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long meetingId = rs.getLong(1);
                    meeting.setId(meetingId);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return meeting;
    }

    @Override
    public boolean update(Meeting meeting) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps = con.prepareStatement(UPDATE_MEETING_BY_ID);
            ps.setString(1, meeting.getCondition().toString());
            ps.setLong(2, meeting.getClient().getId());
            if (meeting.getReviewId() != 0) {
                ps.setLong(3, meeting.getReviewId());
            }
            ps.setLong(4, meeting.getCatalog().getId());
            ps.setTimestamp(5, Timestamp.valueOf(meeting.getDateTime()
                    .plusHours(3)
                    .atZone(ZoneId.of("UTC"))
                    .format(DateTimeFormatter
                            .ofPattern("uuuu-MM-dd HH:mm:ss"))));
            ps.setLong(6, meeting.getId());
            if (ps.executeUpdate() > 0) {
                result = true;
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error("cannot obtain categories", ex);
            }
        } finally {
            dbManager.close(con, ps);
        }
        return result;
    }

    private Meeting extractMeeting(ResultSet rs) throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setId(rs.getLong(Constant.ENTITY_ID));
        switch (rs.getString(Constant.MEETING_CONDITION)) {
            case "ACTIVE":
                meeting.setCondition(Condition.ACTIVE);
                break;
            case "PAID":
                meeting.setCondition(Condition.PAID);
                break;
            case "DONE":
                meeting.setCondition(Condition.DONE);
                break;
        }
        meeting.setClient(new ClientDaoImpl().getById(rs.getLong(Constant.MEETING_ACCOUNT_ID)));
        meeting.setCatalog(new CatalogDaoImpl().getById(rs.getLong(Constant.MEETING_MASTER_SERVICE_ID)));
        meeting.setDateTime(rs.getTimestamp(Constant.MEETING_MEET_TIME).toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
        meeting.setReviewId(rs.getLong(Constant.MEETING_REVIEW_ID));
        return meeting;
    }
}
