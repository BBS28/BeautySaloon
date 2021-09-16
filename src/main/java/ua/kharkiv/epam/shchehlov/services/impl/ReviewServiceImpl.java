package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.ReviewDao;
import ua.kharkiv.epam.shchehlov.entity.Review;
import ua.kharkiv.epam.shchehlov.services.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private final ReviewDao reviewDao;

    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> getAll() {
        return reviewDao.getAll();
    }

    @Override
    public Review getById(Long id) {
        return reviewDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return reviewDao.deleteById(id);
    }

    @Override
    public Review insert(Review item) {
        return reviewDao.insert(item);
    }

    @Override
    public boolean update(Review item) {
        return reviewDao.update(item);
    }
}
