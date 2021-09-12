package ua.kharkiv.epam.shchehlov.entity;

public class Catalog extends Entity {
    private Master master;
    private Service service;

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
