package ua.kharkiv.epam.shchehlov.entity;

public class Master extends Account {
    private Service service;
    private double rate;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
