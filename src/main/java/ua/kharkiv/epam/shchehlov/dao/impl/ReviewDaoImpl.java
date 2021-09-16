package ua.kharkiv.epam.shchehlov.dao.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.ReviewDao;
import ua.kharkiv.epam.shchehlov.dao.db.Constant;
import ua.kharkiv.epam.shchehlov.dao.db.DBManager;
import ua.kharkiv.epam.shchehlov.entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl implements ReviewDao {
    private static final Logger log = Logger.getLogger(ReviewDaoImpl.class);
    private DBManager dbManager = DBManager.getInstance();

    private static final String FIND_ALL_REVIEW = "SELECT * FROM review";
    private static final String FIND_REVIEW_BY_ID = "SELECT * FROM review WHERE id = ?";
    private static final String DELETE_REVIEW_BY_ID = "DELETE FROM review WHERE id = ?";
    private static final String INSERT_NEW_REVIEW = "INSERT INTO review (text, rate) VALUES (?, ?)";
    private static final String UPDATE_REVIEW_BY_ID = "UPDATE review SET text = ?, rate = ? WHERE id = ?";
    private static final String FIND_ALL_REVIEW_WITH_LIMIT = "SELECT * FROM review LIMIT ?, ?";


    @Override
    public List<Review> getAll() {
        List<Review> reviewList = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(FIND_ALL_REVIEW);
            Review review;
            while (rs.next()) {
                review = extractReview(rs);
                reviewList.add(review);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, st, rs);
        }
        return reviewList;
    }

    @Override
    public Review getById(Long reviewId) {
        Review review = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(FIND_REVIEW_BY_ID);
            ps.setLong(1, reviewId);
            rs = ps.executeQuery();
            while (rs.next()) {
                review = extractReview(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return review;
    }

    @Override
    public boolean deleteById(Long id) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(DELETE_REVIEW_BY_ID);
            ps.setLong(1, id);
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
    public Review insert(Review review) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            ps = con.prepareStatement(INSERT_NEW_REVIEW, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, review.getText());
            ps.setInt(2, review.getRate());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Long revievId = rs.getLong(1);
                    review.setId(revievId);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            log.error("cannot obtain categories", ex);
        } finally {
            dbManager.close(con, ps, rs);
        }
        return review;
    }

    @Override
    public boolean update(Review review) {
        PreparedStatement ps = null;
        Connection con = null;
        boolean result = false;
        try {
            con = dbManager.getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps = con.prepareStatement(UPDATE_REVIEW_BY_ID);
            ps.setString(1, review.getText());
            ps.setInt(2, review.getRate());
            ps.setLong(3, review.getId());
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

    private Review extractReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getLong(Constant.ENTITY_ID));
        review.setText(rs.getString(Constant.REVIEW_TEXT));
        review.setRate(rs.getInt(Constant.REVIEW_RATE));
        return review;
    }
}
