package ua.kharkiv.epam.shchehlov.entity;

import java.time.LocalDateTime;

public class Meeting extends Entity {
    private Account client;
    private MasterService masterService;
    private Condition condition;
    private LocalDateTime dateTime;
    private long reviewId;

    public Account getClient() {
        return client;
    }

    public void setClient(Account client) {
        this.client = client;
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

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }
}
