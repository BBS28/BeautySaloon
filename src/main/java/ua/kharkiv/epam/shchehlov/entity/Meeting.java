package ua.kharkiv.epam.shchehlov.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Meeting extends Entity {
    private Account user;
    private MasterService masterService;
    private Condition condition;
    private LocalDateTime dateTime;
    private BigInteger review;

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public MasterService getMasterService() {
        return masterService;
    }

    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigInteger getReview() {
        return review;
    }

    public void setReview(BigInteger review) {
        this.review = review;
    }
}
